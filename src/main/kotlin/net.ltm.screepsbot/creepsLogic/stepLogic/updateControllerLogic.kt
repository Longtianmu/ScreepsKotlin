package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.utils.creepUtils.hasNoEnergy
import screeps.api.*
import screeps.api.structures.StructureController

fun stepUpdateControllerLogic(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.UPGRADE_CONTROLLER.name]?.get("Target")
    val target = Game.getObjectById<StructureController>(targetID)

    if (creep.hasNoEnergy()) {
        return StepReturnCode.SKIP_TICK
    }
    val code = if (target != null) {
        creep.upgradeController(target)
    } else {
        creep.room.controller?.let { creep.upgradeController(it) }
    }
    return when (code) {
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        OK -> StepReturnCode.STATUS_IN_PROGRESS
        ERR_NOT_ENOUGH_RESOURCES -> StepReturnCode.SKIP_TICK
        else -> StepReturnCode.ERR_NEED_RESET
    }
}