package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*
import screeps.api.structures.StructureController

fun stepClaim(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.CLAIM_CONTROLLER.name]?.get("Target")
    val target = Game.getObjectById<StructureController>(targetID)
        ?: return StepReturnCode.ERR_NEED_RESET
    if (creep.body.none { it.type == CLAIM && it.hits != 0 }) {
        return StepReturnCode.OK
    }
    return when (creep.claimController(target)) {
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        OK -> return StepReturnCode.SKIP_TICK
        else -> StepReturnCode.OK
    }
}