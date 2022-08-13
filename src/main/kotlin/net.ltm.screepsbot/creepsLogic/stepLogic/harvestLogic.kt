package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.utils.predict
import screeps.api.*

fun stepHarvest(creep: Creep): StepReturnCode {
    val targetInit = Game.getObjectById<Identifiable>(creep.memory.option[Step.HARVEST.name]?.get("Target"))
    val target = when (creep.memory.option[Step.HARVEST.name]?.get("Type")) {
        "Source" -> {
            targetInit.unsafeCast<Source>()
        }

        "Mineral" -> {
            targetInit.unsafeCast<Mineral>()
        }

        else -> {
            targetInit.unsafeCast<Source>()
        }
    }
    if ((target is Source && target.energyCapacity == 0)) {
        return StepReturnCode.STATUS_IN_PROGRESS
    }
    val ret = when (creep.harvest(target.unsafeCast<Source>())) {
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        else -> StepReturnCode.STATUS_IN_PROGRESS
    }
    if (predict(creep, Step.HARVEST, target)) return StepReturnCode.SKIP_TICK
    return ret
}
