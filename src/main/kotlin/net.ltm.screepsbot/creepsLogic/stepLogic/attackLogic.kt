package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepAttack(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.ATTACK.name]?.get("Target")
    val target = Game.getObjectById<Attackable>(targetID)
        ?: return StepReturnCode.SKIP_TICK

    return when (creep.attack(target)) {
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        OK -> StepReturnCode.STATUS_IN_PROGRESS
        else -> StepReturnCode.OK
    }
}