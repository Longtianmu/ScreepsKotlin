package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.taskRetry
import screeps.api.*

fun stepMove(creep: Creep): TickReturnCode {
    if (creep.memory.taskRetry > 3) {
        return TickReturnCode.ERR_NEED_RESET
    }
    val targetID = creep.memory.option[Step.MOVE.name]?.get("Target")
    val targetRange = creep.memory.option[Step.MOVE.name]?.get("Range")!!.toInt()
    return if (targetID.isNullOrEmpty()) {
        TickReturnCode.ERR_NEED_RESET
    } else {
        val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<HasPosition>()
        if (creep.pos.inRangeTo(target.pos, targetRange)) {
            TickReturnCode.OK
        }
        when (creep.moveTo(target)) {
            OK -> TickReturnCode.STATUS_IN_PROGRESS
            ERR_NO_PATH -> {
                creep.memory.taskRetry++
                TickReturnCode.STATUS_IN_PROGRESS
            }

            else -> TickReturnCode.OK
        }
    }
}