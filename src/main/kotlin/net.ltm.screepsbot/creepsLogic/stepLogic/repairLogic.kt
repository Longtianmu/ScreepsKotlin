package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*
import screeps.api.structures.Structure

fun stepRepair(creep: Creep): TickReturnCode {
    val targetID = creep.memory.option[Step.REPAIR.name]?.get("Target")
    val target = Game.getObjectById<Structure>(targetID).unsafeCast<Structure>()
    return when (creep.repair(target)) {
        ERR_NOT_IN_RANGE -> return TickReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> return TickReturnCode.ERR_NEED_RESET
        OK -> TickReturnCode.STATUS_IN_PROGRESS
        else -> TickReturnCode.OK
    }
}