package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepPickup(creep: Creep): TickReturnCode {
    val targetID = creep.memory.option[Step.PICKUP.name]?.get("Target")
    val target = Game.getObjectById<Resource>(targetID).unsafeCast<Resource>()
    return when (creep.pickup(target)) {
        ERR_NOT_IN_RANGE -> return TickReturnCode.ERR_NEED_MOVE
        ERR_INVALID_TARGET -> return TickReturnCode.ERR_NEED_RESET
        else -> TickReturnCode.OK
    }
}