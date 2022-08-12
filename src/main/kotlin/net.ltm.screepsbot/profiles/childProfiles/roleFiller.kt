package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.Checkers
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.CarrierProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.findStructureNeedFills
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY
import screeps.api.value

class RoleFiller : CarrierProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        val storages = getUsableContainer(creep.room)
        if (storages.isNullOrEmpty()) {
            return GeneratorReturnCode.FAILED
        }
        creep.assignStepOption(Step.WITHDRAW, "Target", storages)
        creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value)
        return GeneratorReturnCode.OK
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        val id = creep.room.findStructureNeedFills() ?: return GeneratorReturnCode.NO_TARGET
        creep.assignStepOption(Step.TRANSFER, "Target", id)
        creep.assignStepOption(Step.TRANSFER, "Type", RESOURCE_ENERGY.value)
        creep.assignStepOption(Step.RE_INIT, "Checker", Checkers.STORED_ENERGY_NONE.name)
        return GeneratorReturnCode.OK
    }
}