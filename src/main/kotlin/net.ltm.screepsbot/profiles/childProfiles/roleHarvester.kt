package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.HarvesterProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import net.ltm.screepsbot.utils.getNearbyContainer
import net.ltm.screepsbot.utils.getNextTarget
import net.ltm.screepsbot.utils.roomUtils.findKSource
import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY
import screeps.api.Source
import screeps.api.value

private fun letFunc(creep: Creep, source: Source, type: String): GeneratorReturnCode {
    creep.assignStepOption(Step.HARVEST, "Target", source.id, false)
    creep.assignStepOption(Step.HARVEST, "Type", "Source", false)
    val target = ((if (creep.room.energyAvailable <= 300) {
        null
    } else {
        getNearbyContainer(source)?.id
    }) ?: getNextTarget(creep.room)) ?: return GeneratorReturnCode.NO_TARGET
    creep.assignStepOption(Step.TRANSFER, "Target", target, false)
    creep.assignStepOption(Step.TRANSFER, "Type", type, false)
    return GeneratorReturnCode.OK
}

class RoleHarvester1 : HarvesterProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        findKSource(creep.room, 0)?.let { source ->
            val target = getNearbyContainer(source) ?: source
            creep.assignStepOption(Step.MOVE, "Target", target.id, false)
        }
        return GeneratorReturnCode.OK
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        var code: GeneratorReturnCode = GeneratorReturnCode.OK
        findKSource(creep.room, 0)?.let {
            code = letFunc(creep, it, "energy")
        }
        return code
    }
}

class RoleHarvester2 : HarvesterProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        findKSource(creep.room, 1)?.let { source ->
            val target = getNearbyContainer(source) ?: source
            creep.assignStepOption(Step.MOVE, "Target", target.id, false)
        }
        return GeneratorReturnCode.OK
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        var code: GeneratorReturnCode = GeneratorReturnCode.OK
        findKSource(creep.room, 1)?.let {
            code = letFunc(creep, it, RESOURCE_ENERGY.value)
        }
        return code
    }
}