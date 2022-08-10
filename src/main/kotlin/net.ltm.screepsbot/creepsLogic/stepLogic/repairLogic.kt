package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*
import screeps.api.structures.Structure

fun stepRepair(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.REPAIR.name]?.get("Target")
    val target = Game.getObjectById<Structure>(targetID).unsafeCast<Structure>()
    return when (creep.repair(target)) {
        ERR_NOT_IN_RANGE -> return StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> return StepReturnCode.ERR_NEED_RESET
        OK -> StepReturnCode.STATUS_IN_PROGRESS
        else -> StepReturnCode.OK
    }
}