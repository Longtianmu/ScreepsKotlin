package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.utils.creepUtils.hasNoEnergy
import screeps.api.*
import screeps.api.structures.Structure

fun stepRepair(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.REPAIR.name]?.get("Target")
    val target = Game.getObjectById<Structure>(targetID) ?: return StepReturnCode.SKIP_TICK

    if (target.hits == target.hitsMax || creep.hasNoEnergy()) {
        return StepReturnCode.SKIP_TICK
    }

    return when (creep.repair(target)) {
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        ERR_NOT_ENOUGH_RESOURCES -> StepReturnCode.SKIP_TICK
        OK -> StepReturnCode.STATUS_IN_PROGRESS
        else -> StepReturnCode.OK
    }
}