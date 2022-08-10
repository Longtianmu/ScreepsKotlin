package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.UpgraderProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.Creep

class RoleUpgrader : UpgraderProfile() {
    override fun initGenerator(creep: Creep) {}

    override fun loopGenerator(creep: Creep) {
        val container = getUsableContainer(creep.room)
        if (container != "null") {
            creep.assignStepOption(Step.WITHDRAW, "Target", container)
            creep.assignStepOption(Step.WITHDRAW, "Type", "energy")
        }
    }
}