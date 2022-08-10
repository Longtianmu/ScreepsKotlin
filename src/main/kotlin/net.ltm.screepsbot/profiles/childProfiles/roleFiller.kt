package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.CarrierProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.findStructureNeedFills
import net.ltm.screepsbot.utils.getNextTarget
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY
import screeps.api.value

class RoleFiller : CarrierProfile() {
    override fun initGenerator(creep: Creep) {
        val storages = getUsableContainer(creep.room)
        creep.assignStepOption(Step.WITHDRAW, "Target", storages)
        creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value)

    }

    override fun loopGenerator(creep: Creep) {
        var id = creep.room.findStructureNeedFills()
        if (id == "null") {
            id = getNextTarget(creep.room)
        }
        creep.assignStepOption(Step.TRANSFER, "Target", id)
        creep.assignStepOption(Step.TRANSFER, "Type", RESOURCE_ENERGY.value)
    }
}