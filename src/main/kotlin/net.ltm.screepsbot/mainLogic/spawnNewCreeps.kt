package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.memory.*
import net.ltm.screepsbot.utils.findStructure
import net.ltm.screepsbot.utils.findStructureNeedFills
import net.ltm.screepsbot.utils.roomUtils.findKSource
import screeps.api.*
import screeps.api.structures.StructureContainer
import screeps.api.structures.StructureSpawn
import screeps.utils.mutableRecordOf
import screeps.utils.unsafe.jsObject

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

fun spawnCreeps(creeps: List<Creep>, spawn: StructureSpawn) {
    val baseBody = arrayOf<BodyPartConstant>(WORK, CARRY, MOVE)
    val baseNoWorkBody = arrayOf<BodyPartConstant>(CARRY, MOVE)
    val maxCreepCount = spawn.room.memory.maxCountMap
    val roleClass: String = roleDetector(creeps, maxCreepCount, spawn) ?: return
    var body: Array<BodyPartConstant>
    val allEnergy = spawn.room.energyAvailable

    if (roleClass in listOf("RoleFiller", "RoleCarrier")) {
        body = baseNoWorkBody
        val baseBodyCost = baseNoWorkBody.sumOf { BODYPART_COST[it]!! }
        if (allEnergy < baseBodyCost) return
        val rate = allEnergy / 2 / baseBodyCost
        for (i in 1 until rate) {
            body = body.plus(baseNoWorkBody)
        }
    } else {
        body = baseBody
        val baseBodyCost = baseBody.sumOf { BODYPART_COST[it]!! }
        var rate = allEnergy / 2 / baseBodyCost
        if (roleClass.contains("RoleHarvester") && rate > 3) {
            rate = 3
        }
        if (allEnergy < baseBodyCost * rate) return
        for (i in 1 until rate) {
            body = body.plus(baseBody)
        }
    }

    val newName = "${roleClass}_${Game.time}"
    val code = spawn.spawnCreep(body, newName, options {
        memory = jsObject<CreepMemory> {
            this.roleClass = roleClass
            this.init = false
            this.option = mutableRecordOf()
            this.taskList = arrayOf()
            this.taskRetry = 0
        }
    })

    when (code) {
        OK -> console.log("使用部件${body}生成了新Creep:${newName}")
        ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { } // do nothing
        else -> console.log("无法解析错误代码$code")
    }
}