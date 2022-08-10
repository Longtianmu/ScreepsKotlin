package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.utils.predict
import screeps.api.*

fun stepHarvest(creep: Creep): StepReturnCode {
    val targetID = creep.memory.option[Step.HARVEST.name]?.get("Target")
    val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<Source>()
    when (creep.harvest(target)) {
        ERR_NOT_IN_RANGE -> return TickReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> return TickReturnCode.ERR_NEED_RESET
    }
    return TickReturnCode.OK
}
