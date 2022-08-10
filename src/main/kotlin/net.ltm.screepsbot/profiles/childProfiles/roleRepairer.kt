package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.RepairerProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.findClosestNotEmpty
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.*

class RoleRepairer : RepairerProfile() {
    override fun initGenerator(creep: Creep) {}

    override fun loopGenerator(creep: Creep) {
        val target = creep.findClosestNotEmpty(
            creep.room.find(FIND_STRUCTURES)
                .filter { it.structureType !in listOf(STRUCTURE_RAMPART, STRUCTURE_WALL) }
                .filter { it.hits < it.hitsMax }
                .toTypedArray()
        )
        creep.assignStepOption(Step.WITHDRAW, "Target", getUsableContainer(creep.room), false)
        creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value, false)
        creep.assignStepOption(Step.REPAIR, "Target", target.id, false)
    }
}