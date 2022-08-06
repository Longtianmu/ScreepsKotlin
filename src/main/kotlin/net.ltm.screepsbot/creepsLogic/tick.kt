package net.ltm.screepsbot.creepsLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.creepsLogic.stepLogic.stepHarvest
import net.ltm.screepsbot.creepsLogic.stepLogic.stepMove
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.taskList
import screeps.api.Creep

fun Creep.tick() {
    val currentTask = memory.taskList.firstOrNull() ?: {

    }
    val returnCode: TickReturnCode = when (currentTask.toString()) {
        Step.MOVE.name -> {
            stepMove(this)
        }

        Step.HARVEST.name -> {
            stepHarvest(this)
        }
        /*Step.BUILD.name -> {

        }
        Step.PICKUP.name -> {

        }
        Step.REPAIR.name -> {

        }
        Step.TRANSFER.name -> {

        }
        Step.WITHDRAW.name -> {

        }
        Step.UPGRADE_CONTROLLER -> {

        }*/
        else -> TickReturnCode.OK
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