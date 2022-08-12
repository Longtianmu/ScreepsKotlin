package net.ltm.screepsbot.creepsLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.bruceReflect
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.constant.returnCode.InitialReturnCode
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.constant.returnCode.StepReturnCode.*
import net.ltm.screepsbot.constant.returnCode.TickReturnCode
import net.ltm.screepsbot.constant.workRange
import net.ltm.screepsbot.creepsLogic.stepLogic.*
import net.ltm.screepsbot.memory.*
import net.ltm.screepsbot.profiles.Profile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.getNextTarget
import screeps.api.Creep
import screeps.api.get
import screeps.utils.mutableRecordOf

fun Creep.init(): InitialReturnCode {
    val roleClass: Profile = bruceReflect(memory.roleClass) ?: return InitialReturnCode.FAILED
    val returnCode: GeneratorReturnCode
    var resultString = memory.roleClass
    if (!memory.init) {
        memory.option = mutableRecordOf()
        memory.taskList = roleClass.preTask.toTypedArray() // taskList
        memory.init = true
        returnCode = roleClass.initGenerator(this) // option
        resultString += ("的Initial阶段")
    } else {
        memory.taskList = roleClass.taskLoop.toTypedArray()
        returnCode = roleClass.loopGenerator(this) // option
        resultString += ("的Loop阶段")
    }
    memory.taskRetry = 0
    return when (returnCode) {
        GeneratorReturnCode.OK -> {
            InitialReturnCode.OK
        }

        GeneratorReturnCode.FAILED -> {
            println(resultString + "发生了错误，请查看是否存在Null情况")
            InitialReturnCode.FAILED
        }

        GeneratorReturnCode.NO_TARGET -> {
            InitialReturnCode.SKIP
        }
    }
}

fun stepDetector(creep: Creep, currentTask: String): StepReturnCode {
    return when (currentTask) {
        Step.MOVE.name -> {
            stepMove(creep)
        }

        Step.HARVEST.name -> {
            stepHarvest(creep)
        }

        Step.TRANSFER.name -> {
            stepTransfer(creep)
        }

        Step.BUILD.name -> {
            stepBuild(creep)
        }

        Step.UPGRADE_CONTROLLER.name -> {
            stepUpdateControllerLogic(creep)
        }

        Step.WITHDRAW.name -> {
            stepWithdraw(creep)
        }

        Step.PICKUP.name -> {
            stepPickup(creep)
        }

        Step.REPAIR.name -> {
            stepRepair(creep)
        }

        Step.RE_INIT.name -> {
            stepReInit(creep)
        }

        else -> OK // unknown Step
    }
}

fun stepReturnCodeProcessor(creep: Creep, code: StepReturnCode, currentTask: String): TickReturnCode {
    return when (code) {
        STATUS_IN_PROGRESS -> {
            TickReturnCode.OK
        }

        OK -> {
            creep.memory.taskList = creep.memory.taskList.toList().drop(1).toTypedArray()
            TickReturnCode.OK
        }

        ERR_NEED_RESET -> {
            creep.memory.taskRetry = 0
            creep.memory.init = false

            creep.memory.taskList = arrayOf()
            creep.memory.option = mutableRecordOf()
            if (creep.store.getUsedCapacity() > 0) {
                val containerID = getNextTarget(creep.room) ?: return TickReturnCode.OK
                creep.memory.taskList = listOf(Step.TRANSFER.name).toTypedArray()
                creep.assignStepOption(Step.TRANSFER, "Target", containerID, true)
            }
            TickReturnCode.REWORK
        }

        ERR_NEED_MOVE -> {
            if (creep.memory.taskList[0] != Step.MOVE.name) {
                creep.memory.taskList = listOf(Step.MOVE.name).plus(creep.memory.taskList.toList()).toTypedArray()
                val target = creep.memory.option[currentTask]?.get("Target")!!
                creep.assignStepOption(Step.MOVE, "Target", target)
                creep.assignStepOption(Step.MOVE, "Range", workRange[currentTask].toString())
            }
            TickReturnCode.REWORK
        }

        SKIP_TICK -> {
            creep.memory.taskList = creep.memory.taskList.toList().drop(1).toTypedArray()
            TickReturnCode.REWORK
        }

        ERR_UNEXPECTED -> {
            TickReturnCode.FAILED
        }
    }
}

fun Creep.tick(): TickReturnCode {
    if (this.spawning) {
        return TickReturnCode.OK
    }
    if (memory.roleClass.isEmpty()) {
        return TickReturnCode.FAILED
    }

    if (memory.taskList.isEmpty()) {
        return when (init()) {
            InitialReturnCode.FAILED -> {
                TickReturnCode.FAILED
            }

            InitialReturnCode.OK -> {
                TickReturnCode.REWORK
            }

            InitialReturnCode.SKIP -> {
                TickReturnCode.OK
            }
        }
    }

    val currentTask = memory.taskList.first()
    return stepReturnCodeProcessor(this, stepDetector(this, currentTask), currentTask)
}
