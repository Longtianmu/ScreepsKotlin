package net.ltm.screepsbot.creepsLogic.tickLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.constant.returnCode.TickReturnCode
import net.ltm.screepsbot.constant.workRange
import net.ltm.screepsbot.memory.init
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.taskList
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.getNextTarget
import screeps.api.Creep
import screeps.api.get
import screeps.utils.mutableRecordOf

fun stepReturnCodeProcessor(creep: Creep, code: StepReturnCode, currentTask: String): TickReturnCode {
    return when (code) {
        StepReturnCode.STATUS_IN_PROGRESS -> {
            TickReturnCode.OK
        }

        StepReturnCode.OK -> {
            creep.memory.taskList = creep.memory.taskList.toList().drop(1).toTypedArray()
            TickReturnCode.OK
        }

        StepReturnCode.ERR_NEED_RESET -> {
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

        StepReturnCode.ERR_NEED_MOVE -> {
            if (creep.memory.taskList[0] != Step.MOVE.name) {
                creep.memory.taskList = listOf(Step.MOVE.name).plus(creep.memory.taskList.toList()).toTypedArray()
                val target = creep.memory.option[currentTask]?.get("Target")!!
                creep.assignStepOption(Step.MOVE, "Target", target)
                creep.assignStepOption(Step.MOVE, "Range", workRange[currentTask].toString())
            }
            TickReturnCode.REWORK
        }

        StepReturnCode.SKIP_TICK -> {
            creep.memory.taskList = creep.memory.taskList.toList().drop(1).toTypedArray()
            TickReturnCode.REWORK
        }

        StepReturnCode.ERR_UNEXPECTED -> {
            TickReturnCode.FAILED
        }
    }
}