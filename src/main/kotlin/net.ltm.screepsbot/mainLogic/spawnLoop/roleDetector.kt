package net.ltm.screepsbot.mainLogic.spawnLoop

import net.ltm.screepsbot.memory.maxCountMap
import net.ltm.screepsbot.memory.roleClass
import net.ltm.screepsbot.utils.findStructure
import net.ltm.screepsbot.utils.findStructureNeedFills
import net.ltm.screepsbot.utils.roomUtils.findKSource
import screeps.api.*
import screeps.api.structures.StructureContainer
import screeps.api.structures.StructureSpawn

fun roleDetector(creeps: List<Creep>, spawn: StructureSpawn): Pair<String, Array<BodyPartConstant>>? {//Role和身体组成
    val maxCreepCount: MutableRecord<String, Int> = spawn.room.memory.maxCountMap
    val roleClass = when {
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
    val baseBody = arrayOf<BodyPartConstant>(WORK, CARRY, MOVE)
    val baseNoWorkBody = arrayOf<BodyPartConstant>(CARRY, MOVE)
    var body: Array<BodyPartConstant>
    val allEnergy = spawn.room.energyAvailable
    if (allEnergy < baseBody.sumOf { BODYPART_COST[it]!! }) {
        return null
    }
    if (roleClass in listOf("RoleFiller", "RoleCarrier")) {
        body = baseNoWorkBody
        val baseBodyCost = baseNoWorkBody.sumOf { BODYPART_COST[it]!! }
        if (allEnergy < baseBodyCost) return null
        val rate = allEnergy / 2 / baseBodyCost
        for (i in 1 until rate) {
            body = body.plus(baseNoWorkBody)
        }
    } else {
        body = baseBody
        val baseBodyCost = baseBody.sumOf { BODYPART_COST[it]!! }
        var rate = allEnergy / 2 / baseBodyCost
        if (roleClass != null) {
            if (roleClass.contains("RoleHarvester") && rate > 3) {
                rate = 3
            }
        }
        for (i in 1 until rate) {
            body = body.plus(baseBody)
        }
    }
    return Pair(roleClass ?: return null, body)
    TODO("使用人均期望值和最大爬数来控制生成")
}

