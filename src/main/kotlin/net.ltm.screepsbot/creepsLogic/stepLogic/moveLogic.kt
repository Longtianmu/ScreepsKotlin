package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.taskRetry
import screeps.api.*

fun stepMove(creep: Creep): StepReturnCode {
    if (creep.memory.taskRetry > 4) {
        return StepReturnCode.ERR_NEED_RESET
    }
    val targetID = creep.memory.option[Step.MOVE.name]?.get("Target")
    val targetRange = if (creep.memory.option[Step.MOVE.name]?.get("Range") != null) {
        creep.memory.option[Step.MOVE.name]?.get("Range")!!.toInt()
    } else 1
    return if (targetID.isNullOrEmpty()) {
        StepReturnCode.ERR_NEED_RESET
    } else {
        val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<HasPosition?>()
            ?: return StepReturnCode.ERR_NEED_RESET
        if (creep.pos.inRangeTo(target.pos, targetRange)) {
            return StepReturnCode.SKIP_TICK
        }
        ALGORITHM_ASTAR
        when (creep.moveTo(target)) {
            OK -> StepReturnCode.STATUS_IN_PROGRESS
            ERR_NO_PATH -> {
                creep.memory.taskRetry++
                StepReturnCode.STATUS_IN_PROGRESS
            }

            else -> StepReturnCode.OK
        }
    }
}