package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepBuild(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.BUILD.name]?.get("Target")
    val target = Game.getObjectById<ConstructionSite>(targetID)
        ?: return StepReturnCode.ERR_NEED_RESET
    return when (creep.build(target)) {
        ERR_NOT_FOUND -> StepReturnCode.ERR_NEED_RESET
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        ERR_NOT_ENOUGH_RESOURCES -> StepReturnCode.SKIP_TICK
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        OK -> return StepReturnCode.STATUS_IN_PROGRESS
        else -> StepReturnCode.OK
    }
}