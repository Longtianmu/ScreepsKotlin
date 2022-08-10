package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepBuild(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.BUILD.name]?.get("Target")
    val target = Game.getObjectById<ConstructionSite>(targetID).unsafeCast<ConstructionSite>()
    return when (target?.let { creep.build(it) }) {
        ERR_NOT_FOUND -> return StepReturnCode.ERR_NEED_RESET
        ERR_NOT_IN_RANGE -> return StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> return StepReturnCode.ERR_NEED_RESET
        OK -> return StepReturnCode.STATUS_IN_PROGRESS
        else -> StepReturnCode.OK
    }
}