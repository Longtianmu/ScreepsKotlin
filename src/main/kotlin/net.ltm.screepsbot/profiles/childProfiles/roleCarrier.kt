package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.CarrierProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.findClosest
import net.ltm.screepsbot.utils.findStructure
import net.ltm.screepsbot.utils.getNextTarget
import screeps.api.*

class RoleCarrier : CarrierProfile() {
    override fun initGenerator(creep: Creep) {}

    override fun loopGenerator(creep: Creep) {
        creep.findClosest(creep.room.findStructure(STRUCTURE_CONTAINER)
            .filter {
                it.unsafeCast<StoreOwner>().store.getUsedCapacity(RESOURCE_ENERGY) > 0
            }
        )?.let {
            creep.assignStepOption(Step.WITHDRAW, "Target", it.id)
            creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value)
        }
        val transfer = getNextTarget(creep.room)
        if (transfer != "null") {
            creep.assignStepOption(Step.TRANSFER, "Target", transfer)
            creep.assignStepOption(Step.TRANSFER, "Type", RESOURCE_ENERGY.value)
        }
    }
}