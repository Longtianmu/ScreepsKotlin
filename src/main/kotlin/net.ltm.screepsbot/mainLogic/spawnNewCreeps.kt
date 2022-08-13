package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.memory.roleClass
import net.ltm.screepsbot.utils.findStructure
import net.ltm.screepsbot.utils.findStructureNeedFills
import net.ltm.screepsbot.utils.roomUtils.findKSource
import screeps.api.*
import screeps.api.structures.StructureContainer
import screeps.api.structures.StructureSpawn

fun roleDetector(creeps: List<Creep>, maxCreepCount: MutableRecord<String, Int>, spawn: StructureSpawn): String? {
    return when {
        creeps.count { it.memory.roleClass == "RoleHarvester1" } < maxCreepCount["RoleHarvester1"] -> "RoleHarvester1"

        findKSource(spawn.room, 1) != null &&
                creeps.count { it.memory.roleClass == "RoleHarvester2" } < maxCreepCount["RoleHarvester2"] -> "RoleHarvester2"

        spawn.room.findStructure<StructureContainer>().isNotEmpty() &&
                creeps.count { it.memory.roleClass == "RoleCarrier" } < maxCreepCount["RoleCarrier"] -> "RoleCarrier"

        creeps.count { it.memory.roleClass == "RoleUpgrader" } < maxCreepCount["RoleUpgrader"] -> "RoleUpgrader"

        creeps.count { it.memory.roleClass == "RoleRepairer" } < maxCreepCount["RoleRepairer"] -> "RoleRepairer"

        spawn.room.findStructureNeedFills() != null &&
                creeps.count { it.memory.roleClass == "RoleFiller" } < maxCreepCount["RoleFiller"] -> "RoleFiller"

        spawn.room.find(FIND_MY_CONSTRUCTION_SITES).isNotEmpty() && spawn.room.energyAvailable >= 300 &&
                creeps.count { it.memory.roleClass == "RoleBuilder" } < maxCreepCount["RoleBuilder"] -> "RoleBuilder"

        else -> null
    }
}

