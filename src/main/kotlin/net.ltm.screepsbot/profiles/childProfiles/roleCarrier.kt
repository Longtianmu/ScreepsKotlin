package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.Checkers
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.CarrierProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.findStructure
import net.ltm.screepsbot.utils.getNextTarget
import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY
import screeps.api.compareTo
import screeps.api.structures.StructureContainer
import screeps.api.value

class RoleCarrier : CarrierProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        val target = creep.room.findStructure<StructureContainer>()
            .filter { it.store.getUsedCapacity(RESOURCE_ENERGY) > 0 }
            .sortedByDescending { it.store.getUsedCapacity(RESOURCE_ENERGY) }
            .firstOrNull() ?: creep.room.storage
        return if (target != null) {
            creep.assignStepOption(Step.WITHDRAW, "Target", target.id)
            creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value)
            GeneratorReturnCode.OK
        } else {
            GeneratorReturnCode.NO_TARGET
        }
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        val transfer = getNextTarget(creep.room) ?: return GeneratorReturnCode.NO_TARGET
        creep.assignStepOption(Step.TRANSFER, "Target", transfer)
        creep.assignStepOption(Step.TRANSFER, "Type", RESOURCE_ENERGY.value)
        creep.assignStepOption(Step.RE_INIT, "Checker", Checkers.STORED_ENERGY_NONE.name)
        return GeneratorReturnCode.OK
    }
}