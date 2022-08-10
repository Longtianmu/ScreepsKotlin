package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepTransfer(creep: Creep): StepReturnCode {
    creep.memory.option[Step.TRANSFER.name]?.let {
        val targetID = it["Target"]
        val amount = it["Amount"]
        val type = it["Type"].unsafeCast<ResourceConstant>()
        Game.getObjectById<Identifiable>(targetID)?.let { target ->
            return if (amount.isNullOrEmpty()) {
                when (creep.transfer(target.unsafeCast<StoreOwner>(), type)) {
                    ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
                    ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
                    else -> StepReturnCode.SKIP_TICK
                }
            } else {
                when (creep.transfer(target.unsafeCast<StoreOwner>(), type, amount.toInt())) {
                    ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
                    ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
                    else -> StepReturnCode.SKIP_TICK
                }
            }
        }
    }
    return StepReturnCode.ERR_NEED_RESET
}
