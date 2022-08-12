package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.Checkers
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.RepairerProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.*

class RoleRepairer : RepairerProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        val target = getUsableContainer(creep.room) ?: return GeneratorReturnCode.NO_TARGET
        creep.assignStepOption(Step.WITHDRAW, "Target", target, false)
        creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value, false)
        return GeneratorReturnCode.OK
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        val target = creep.room.find(FIND_STRUCTURES)
            .filter { it.structureType !in listOf(STRUCTURE_RAMPART, STRUCTURE_WALL) }
            .filter { it.hits < it.hitsMax }
            .sortedBy { it.hits }
            .toTypedArray()
            .firstOrNull() ?: return GeneratorReturnCode.NO_TARGET
        creep.assignStepOption(Step.REPAIR, "Target", target.id, false)
        creep.assignStepOption(Step.RE_INIT, "Checker", Checkers.STORED_ENERGY_NONE.name)
        return GeneratorReturnCode.OK
    }
}