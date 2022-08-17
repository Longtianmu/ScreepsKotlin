package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.option
import screeps.api.*

fun stepTransfer(creep: Creep): StepReturnCode {
    creep.memory.option[Step.TRANSFER.name]?.let {
        val targetID = it["Target"]
        val amount = it["Amount"]
        val type = it["Type"].unsafeCast<ResourceConstant>()
        val target = Game.getObjectById<StoreOwner>(targetID)

        if (creep.store.getUsedCapacity() == 0) {
            return StepReturnCode.SKIP_TICK
        }

        target?.let { targets ->
            if (targets.store.getFreeCapacity() == 0) {
                return StepReturnCode.SKIP_TICK
            }
            val code = if (amount.isNullOrEmpty()) {
                creep.transfer(targets, type)
            } else {
                creep.transfer(targets, type, amount.toInt())
            }
            return when (code) {
                ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
                ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
                ERR_FULL -> StepReturnCode.OK
                else -> StepReturnCode.SKIP_TICK
            }
        }
    }
    return StepReturnCode.ERR_NEED_RESET
}
