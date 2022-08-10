package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.HarvesterProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.getNearbyContainer
import screeps.api.Creep
import screeps.api.FIND_SOURCES
import screeps.api.Source

private fun findKSource(creep: Creep, k: Int): Source? {
    val sources = creep.room.find(FIND_SOURCES)
    return sources[k]
}

class RoleHarvester1 : HarvesterProfile() {
    override fun initGenerator(creep: Creep) {
        findKSource(creep, 0)?.let { source ->
            getNearbyContainer(source)?.let {
                creep.assignStepOption(Step.MOVE, "Target", it.id, false)
            }
        }
    }

    override fun loopGenerator(creep: Creep) {
        findKSource(creep, 0)?.let { source ->
            creep.assignStepOption(Step.HARVEST, "Target", source.id, false)
        }
    }
}

class RoleHarvester2 : HarvesterProfile() {
    override fun initGenerator(creep: Creep) {
        findKSource(creep, 1)?.let { source ->
            getNearbyContainer(source)?.let {
                creep.assignStepOption(Step.MOVE, "Target", it.id, false)
            }
        }
    }

    override fun loopGenerator(creep: Creep) {
        findKSource(creep, 1)?.let { source ->
            creep.assignStepOption(Step.HARVEST, "Target", source.id, false)
        }
    }
}