package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepWithdraw(creep: Creep): StepReturnCode {
    val temp = creep.memory.option[Step.WITHDRAW.name]
    val targetID = temp?.get("Target")
    val amount = temp?.get("Amount")
    val type = temp?.get("Type").unsafeCast<ResourceConstant>()
    val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<StoreOwner?>()
        ?: return StepReturnCode.ERR_NEED_RESET
    return if (amount.isNullOrEmpty()) {
        when (creep.withdraw(target, type)) {
            ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
            ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
            else -> StepReturnCode.OK
        }
    } else {
        when (creep.withdraw(target, type, amount.toInt())) {
            ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
            ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
            else -> StepReturnCode.OK
        }
    }
}