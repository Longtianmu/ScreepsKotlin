package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.resourceMap
import screeps.api.*

fun stepWithdraw(creep: Creep): TickReturnCode {
    val temp = creep.memory.option[Step.WITHDRAW.name]
    val targetID = temp?.get("Target")
    val amount = temp?.get("Amount")
    val type = resourceMap[temp?.get("Type")]!!
    val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<StoreOwner?>()
    return if (amount.isNullOrEmpty()) {
        when (creep.withdraw(target!!, type)) {
            ERR_INVALID_TARGET -> TickReturnCode.ERR_NEED_RESET
            ERR_NOT_IN_RANGE -> TickReturnCode.ERR_NEED_MOVE
            else -> TickReturnCode.OK
        }
    } else {
        when (creep.withdraw(target!!, type, amount.toInt())) {
            ERR_INVALID_TARGET -> TickReturnCode.ERR_NEED_RESET
            ERR_NOT_IN_RANGE -> TickReturnCode.ERR_NEED_MOVE
            else -> TickReturnCode.OK
        }
    }
}