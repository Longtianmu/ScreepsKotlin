package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.StepReturnCode
import net.ltm.screepsbot.memory.init
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.roleClass
import net.ltm.screepsbot.memory.taskList
import screeps.api.*
import screeps.utils.mutableRecordOf

fun stepTransfer(creep: Creep): StepReturnCode {
    creep.memory.option[Step.TRANSFER.name]?.let {
        val targetID = it["Target"]
        val amount = it["Amount"]
        val type = it["Type"].unsafeCast<ResourceConstant>()
        val target = Game.getObjectById<Identifiable>(targetID)
        if (creep.memory.roleClass == "RoleCarrier" && creep.store.getUsedCapacity(RESOURCE_ENERGY) == 0) {
            creep.memory.init = false
            creep.memory.taskList = arrayOf()
            creep.memory.option = mutableRecordOf()
            return StepReturnCode.SKIP_TICK
        }
        target?.let { targets ->
            if (targets.unsafeCast<StoreOwner>().store.getFreeCapacity() == 0) {
                return StepReturnCode.SKIP_TICK
            }
            return if (amount.isNullOrEmpty()) {
                when (creep.transfer(targets.unsafeCast<StoreOwner>(), type)) {
                    ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
                    ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
                    else -> StepReturnCode.SKIP_TICK
                }
            } else {
                when (creep.transfer(targets.unsafeCast<StoreOwner>(), type, amount.toInt())) {
                    ERR_INVALID_TARGET -> StepReturnCode.ERR_NEED_RESET
                    ERR_NOT_IN_RANGE -> StepReturnCode.ERR_NEED_MOVE
                    else -> StepReturnCode.SKIP_TICK
                }
            }
        }
    }
    return StepReturnCode.ERR_NEED_RESET
}
