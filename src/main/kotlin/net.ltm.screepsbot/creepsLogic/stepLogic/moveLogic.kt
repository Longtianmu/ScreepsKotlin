package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepMove(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.MOVE.name]?.get("Target")
    val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<HasPosition?>()
        ?: return StepReturnCode.ERR_NEED_RESET

    val targetRange = if (creep.memory.option[Step.MOVE.name]?.get("Range") != null) {
        creep.memory.option[Step.MOVE.name]?.get("Range")!!.toInt()
    } else 1
    if (creep.pos.inRangeTo(target.pos, targetRange)) {
        return StepReturnCode.SKIP_TICK
    }
    return when (creep.moveTo(target)) {
        OK -> StepReturnCode.STATUS_IN_PROGRESS
        ERR_NO_PATH -> {
            StepReturnCode.STATUS_IN_PROGRESS
        }

        else -> StepReturnCode.OK
    }
}