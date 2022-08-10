package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.HarvesterProfile
import net.ltm.screepsbot.utils.assignStepOption
import net.ltm.screepsbot.utils.getNearbyContainer
import screeps.api.*

private fun findKSource(creep: Creep, k: Int): Source? {
    val sources = creep.room.find(FIND_SOURCES)
    return sources[k].unsafeCast<Source?>()
}

private fun letFunc(creep: Creep, source: Source, type: String) {
    creep.assignStepOption(Step.HARVEST, "Target", source.id, false)
    getNearbyContainer(source)?.let {
        creep.assignStepOption(Step.TRANSFER, "Target", it.id, false)
        creep.assignStepOption(Step.TRANSFER, "Type", type, false)
    }
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
        findKSource(creep, 0)?.let {
            letFunc(creep, it, "energy")
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
        findKSource(creep, 1)?.let {
            letFunc(creep, it, RESOURCE_ENERGY.value)
        }
    }
}