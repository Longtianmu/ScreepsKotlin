package net.ltm.screepsbot.creepsLogic.roleLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.constant.manualReflect
import net.ltm.screepsbot.constant.workRange
import net.ltm.screepsbot.creepsLogic.stepLogic.*
import net.ltm.screepsbot.memory.*
import net.ltm.screepsbot.profiles.Profile
import net.ltm.screepsbot.utils.assignStepOption
import screeps.api.Creep
import screeps.api.get
import screeps.utils.mutableRecordOf

fun initials(creep: Creep, roleCls: Profile) {
    if (!creep.memory.init) {
        println("init${creep.name}")
        creep.memory.taskList = roleCls.preTask.toTypedArray() // taskList
        roleCls.initGenerator(creep) // option
        creep.memory.init = true
    } else {
        creep.memory.taskList = roleCls.taskLoop.toTypedArray()
        roleCls.loopGenerator(creep) // option
    }
    creep.memory.taskRetry = 0
}

fun Creep.tick() {
    val roleCls = manualReflect[memory.roleClass] ?: return
    if (memory.taskList.isEmpty()) {
        initials(this, roleCls)
        return
    }
    val currentTask = memory.taskList.first()
    val returnCode: TickReturnCode = when (currentTask) {
        Step.MOVE.name -> {
            stepMove(this)
        }

        Step.HARVEST.name -> {
            stepHarvest(this)
        }

        Step.TRANSFER.name -> {
            stepTransfer(this)
        }

        Step.BUILD.name -> {
            stepBuild(this)
        }

        Step.UPGRADE_CONTROLLER.name -> {
            stepUpdateControllerLogic(this)
        }

        Step.WITHDRAW.name -> {
            stepWithdraw(this)
        }

        Step.PICKUP.name -> {
            stepPickup(this)
        }

        Step.REPAIR.name -> {
            stepRepair(this)
        }

        else -> TickReturnCode.OK // unknown Step
    }

    when (returnCode) {
        TickReturnCode.STATUS_IN_PROGRESS -> {
            return
        }

        TickReturnCode.OK -> {
            memory.taskList = memory.taskList.toList().drop(1).toTypedArray()
        }

        TickReturnCode.ERR_NEED_RESET -> {
            memory.init = false
            val containerID = Step.TRANSFER.getTarget(this.room)
            memory.taskList = arrayOf()
            memory.option = mutableRecordOf()
            if (store.getUsedCapacity() > 0) {
                memory.taskList = memory.taskList.toList().plus(Step.TRANSFER.name).toTypedArray()
                assignStepOption(Step.TRANSFER, "Target", containerID, true)
            }
        }

        TickReturnCode.ERR_NEED_MOVE -> {
            if (memory.taskList[0] != Step.MOVE.name) {
                memory.taskList = listOf(Step.MOVE.name).plus(memory.taskList.toList()).toTypedArray()
                val target = memory.option[currentTask]?.get("Target")!!
                assignStepOption(Step.MOVE, "Target", target)
                assignStepOption(Step.MOVE, "Range", workRange[currentTask].toString())
            }
        }
    }
}
