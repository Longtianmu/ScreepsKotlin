package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.utils.predict
import screeps.api.*

fun stepHarvest(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.HARVEST.name]?.get("Target")
    val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<Source>()
    val ret = when (creep.harvest(target)) {
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        else -> StepReturnCode.STATUS_IN_PROGRESS
    }
    if (predict(creep, Step.HARVEST, target)) return StepReturnCode.SKIP_TICK
    return ret
}
