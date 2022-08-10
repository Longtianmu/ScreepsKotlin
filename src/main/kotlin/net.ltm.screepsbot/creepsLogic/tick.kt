package net.ltm.screepsbot.creepsLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.creepsLogic.stepLogic.*
import net.ltm.screepsbot.memory.*
import screeps.api.Creep

fun Creep.tick() {
    val roleCls = manualReflect[memory.roleClass] ?: return

    val currentTask = memory.taskList.firstOrNull() ?: {
        if (!memory.init) {
            memory.taskList = roleCls.preTask // taskList
            roleCls.initGenerator(this) // option
            memory.init = true
        } else {
            memory.taskList = roleCls.taskLoop // taskList
            roleCls.loopGenerator(this) // option
        }

        memory.taskRetry = 0

        memory.taskList.firstOrNull()

//        val res = profileLoader(memory.baseProfile)
//        memory.taskList = res.taskList
//        memory.option = res.options

    }
    val returnCode: TickReturnCode = when (currentTask.toString()) {
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
        TickReturnCode.OK -> {
            memory.taskList.removeFirst()
        }

        TickReturnCode.ERR_NEED_RESET -> {
            val containerID = Step.TRANSFER.getTarget(this.room)
            memory.taskList = mutableListOf()
            memory.option.clear()
            if (store.getUsedCapacity() > 0) {
                memory.taskList.add(Step.TRANSFER.name)
                memory.option[Step.TRANSFER.name] = mutableMapOf()
                memory.option[Step.TRANSFER.name]?.set("Target", containerID)
            }
        }

        TickReturnCode.ERR_NEED_MOVE -> {
            memory.taskList.add(0, Step.MOVE.name)
            val target = memory.option[currentTask]?.get("Target")!!
            memory.option[Step.MOVE.name]?.set("Target", target)
        }
    }
}