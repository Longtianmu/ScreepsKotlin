package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.RepairerProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.*

class RoleRepairer : RepairerProfile() {
    override fun initGenerator(creep: Creep) {
        creep.assignStepOption(Step.WITHDRAW, "Target", getUsableContainer(creep.room), false)
        creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value, false)
    }

    override fun loopGenerator(creep: Creep) {
        val target = creep.room.find(FIND_STRUCTURES)
            .filter { it.structureType !in listOf(STRUCTURE_RAMPART, STRUCTURE_WALL) }
            .filter { it.hits < it.hitsMax }
            .sortedBy { it.hits }
            .toTypedArray()
            .first()
        creep.assignStepOption(Step.REPAIR, "Target", target.id, false)
    }
}