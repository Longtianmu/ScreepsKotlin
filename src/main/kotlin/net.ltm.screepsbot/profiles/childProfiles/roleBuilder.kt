package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.BuilderProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.Creep
import screeps.api.FIND_MY_CONSTRUCTION_SITES

class RoleBuilder : BuilderProfile() {
    override fun initGenerator(creep: Creep) {}

    override fun loopGenerator(creep: Creep) {
        val constructionSites = creep.room.find(FIND_MY_CONSTRUCTION_SITES)
        val ids = constructionSites.firstOrNull() ?: return
        creep.assignStepOption(Step.WITHDRAW, "Target", getUsableContainer(creep.room), false)
        creep.assignStepOption(Step.WITHDRAW, "Type", "energy", false)
        creep.assignStepOption(Step.BUILD, "Target", ids.id, false)
    }
}