package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*
import screeps.api.structures.StructureController

fun stepUpdateControllerLogic(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.UPGRADE_CONTROLLER.name]?.get("Target")
    val target = Game.getObjectById<StructureController>(targetID)
    return if (target != null) {
        when (creep.upgradeController(target)) {
            ERR_NOT_IN_RANGE -> return StepReturnCode.ERR_NEED_MOVE
            ERR_INVALID_TARGET -> return StepReturnCode.ERR_NEED_RESET
            OK -> return StepReturnCode.STATUS_IN_PROGRESS
            ERR_NOT_ENOUGH_RESOURCES -> StepReturnCode.OK
            else -> StepReturnCode.ERR_NEED_RESET
        }
    } else {
        when (creep.room.controller?.let { creep.upgradeController(it) }) {
            ERR_INVALID_TARGET -> return StepReturnCode.ERR_NEED_RESET
            ERR_NOT_IN_RANGE -> return StepReturnCode.ERR_NEED_MOVE
            OK -> return StepReturnCode.STATUS_IN_PROGRESS
            ERR_NOT_ENOUGH_RESOURCES -> StepReturnCode.OK
            else -> StepReturnCode.ERR_NEED_RESET
        }
    }
}