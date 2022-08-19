package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepDismantle(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.DISMANTLE.name]?.get("Target")
    val target = Game.getObjectById<IStructure>(targetID)
        ?: return StepReturnCode.SKIP_TICK

    return when (creep.dismantle(target)) {
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        OK -> return StepReturnCode.STATUS_IN_PROGRESS
        else -> StepReturnCode.OK
    }
}