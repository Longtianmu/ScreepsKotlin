package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.init
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.roleClass
import screeps.api.*

fun stepTransfer(creep: Creep): StepReturnCode {
    creep.memory.option[Step.TRANSFER.name]?.let {
        val targetID = it["Target"]
        val amount = it["Amount"]
        val type = it["Type"].unsafeCast<ResourceConstant>()
        val target = Game.getObjectById<StoreOwner>(targetID)

        if (creep.memory.roleClass in listOf(
                "RoleCarrier",
                "RoleFiller"
            ) && creep.store.getUsedCapacity(RESOURCE_ENERGY) == 0
        ) {
            creep.memory.init = false
            return StepReturnCode.SKIP_TICK
        }

        target?.let { targets ->
            if (targets.store.getFreeCapacity() == 0) {
                return StepReturnCode.SKIP_TICK
            }
            return if (amount.isNullOrEmpty()) {
                when (creep.transfer(targets, type)) {
                    ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
                    ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
                    else -> StepReturnCode.SKIP_TICK
                }
            } else {
                when (creep.transfer(targets, type, amount.toInt())) {
                    ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
                    ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
                    else -> StepReturnCode.SKIP_TICK
                }
            }
        }
    }
    return StepReturnCode.ERR_NEED_RESET
}
