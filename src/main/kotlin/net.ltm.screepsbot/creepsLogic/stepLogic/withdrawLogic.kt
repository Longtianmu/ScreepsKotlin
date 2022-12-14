package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepWithdraw(creep: Creep): StepReturnCode {
    val temp = creep.memory.option[Step.WITHDRAW.name]
    val targetID = temp?.get("Target")
    val amount = temp?.get("Amount")
    val type = temp?.get("Type").unsafeCast<ResourceConstant>()
    val target = Game.getObjectById<StoreOwner>(targetID)
        ?: return StepReturnCode.ERR_NEED_RESET
    if (creep.store.getFreeCapacity(type) == 0) {
        return StepReturnCode.SKIP_TICK
    }

    val code = if (amount.isNullOrEmpty()) {
        creep.withdraw(target, type)
    } else {
        creep.withdraw(target, type, amount.toInt())
    }
    return when (code) {
        ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
        ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
        else -> StepReturnCode.OK
    }
}