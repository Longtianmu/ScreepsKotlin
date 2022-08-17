package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.Checkers
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.UpgraderProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.getUsableContainer
import net.ltm.screepsbot.utils.roomUtils.lookInRange
import screeps.api.*
import screeps.api.structures.StructureLink

class RoleUpgrader : UpgraderProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        val links =
            creep.room.controller?.pos?.lookInRange(LOOK_STRUCTURES, 4)?.filterIsInstance<StructureLink>()
                ?.firstOrNull {
                    it.store.getUsedCapacity(RESOURCE_ENERGY) > 0
                }?.id
        val container = getUsableContainer(creep.room)
        val target = links ?: container ?: return GeneratorReturnCode.FAILED
        creep.assignStepOption(Step.WITHDRAW, "Target", target)
        creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value)
        return GeneratorReturnCode.OK
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        creep.assignStepOption(Step.UPGRADE_CONTROLLER, "Target", creep.room.controller?.id!!)
        creep.assignStepOption(Step.RE_INIT, "Checker", Checkers.STORED_ENERGY_NONE.name)
        return GeneratorReturnCode.OK
    }
}