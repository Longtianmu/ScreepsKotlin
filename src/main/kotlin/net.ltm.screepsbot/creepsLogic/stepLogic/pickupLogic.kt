package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepPickup(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.PICKUP.name]?.get("Target")
    val target = Game.getObjectById<Resource>(targetID) ?: return StepReturnCode.SKIP_TICK
    return when (creep.pickup(target)) {
        ERR_NOT_IN_RANGE -> return StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> return StepReturnCode.ERR_NEED_RESET
        else -> StepReturnCode.OK
    }
}