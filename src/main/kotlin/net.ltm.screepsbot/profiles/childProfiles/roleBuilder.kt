package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.Checkers
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.BuilderProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.Creep
import screeps.api.FIND_MY_CONSTRUCTION_SITES
import screeps.api.RESOURCE_ENERGY
import screeps.api.value

class RoleBuilder : BuilderProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        val target = getUsableContainer(creep.room) ?: return GeneratorReturnCode.FAILED
        creep.assignStepOption(Step.WITHDRAW, "Target", target)
        creep.assignStepOption(Step.WITHDRAW, "Type", RESOURCE_ENERGY.value)
        return GeneratorReturnCode.OK
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        val constructionSites = creep.room.find(FIND_MY_CONSTRUCTION_SITES)
        val target = constructionSites.firstOrNull() ?: return GeneratorReturnCode.NO_TARGET
        creep.assignStepOption(Step.BUILD, "Target", target.id)
        creep.assignStepOption(Step.RE_INIT, "Checker", Checkers.STORED_ENERGY_NONE.name)
        return GeneratorReturnCode.OK
    }
}