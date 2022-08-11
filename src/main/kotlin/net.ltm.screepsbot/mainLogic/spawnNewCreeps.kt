package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.memory.*
import screeps.api.*
import screeps.api.structures.StructureSpawn
import screeps.utils.mutableRecordOf
import screeps.utils.unsafe.jsObject

fun spawnCreeps(creeps: List<Creep>, spawn: StructureSpawn) {
    if (spawn.room.controller!!.level >= 4) {
        spawnCreepsHigh(creeps, spawn)
    } else {
        spawnCreepsLow(creeps, spawn)
    }
}

fun spawnCreepsHigh(creeps: List<Creep>, spawn: StructureSpawn) {
    val baseBody = arrayOf<BodyPartConstant>(WORK, CARRY, MOVE)
    val baseNoWorkBody = arrayOf<BodyPartConstant>(CARRY, MOVE)
    val maxCreepCount = spawn.room.memory.maxCountMap
    val roleClass: String = when {
        creeps.count { it.memory.roleClass == "RoleHarvester1" } < maxCreepCount["RoleHarvester1"] -> "RoleHarvester1"

        creeps.count { it.memory.roleClass == "RoleHarvester2" } < maxCreepCount["RoleHarvester2"] -> "RoleHarvester2"

        creeps.count { it.memory.roleClass == "RoleCarrier" } < maxCreepCount["RoleCarrier"] -> "RoleCarrier"

        creeps.count { it.memory.roleClass == "RoleUpgrader" } < maxCreepCount["RoleUpgrader"] -> "RoleUpgrader"

        creeps.count { it.memory.roleClass == "RoleRepairer" } < maxCreepCount["RoleRepairer"] -> "RoleRepairer"

        creeps.count { it.memory.roleClass == "RoleFiller" } < maxCreepCount["RoleFiller"] -> "RoleFiller"

        spawn.room.find(FIND_MY_CONSTRUCTION_SITES).isNotEmpty() &&
                creeps.count { it.memory.roleClass == "RoleBuilder" } < maxCreepCount["RoleBuilder"] -> "RoleBuilder"

        else -> return
    }
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
        if (roleClass.contains("RoleHarvester")) {
            rate = 2
        }
        if (allEnergy < baseBodyCost) return
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

fun spawnCreepsLow(creeps: List<Creep>, spawn: StructureSpawn) {
    var baseBody = arrayOf<BodyPartConstant>(WORK, CARRY, MOVE)
    if (spawn.room.energyAvailable < baseBody.sumOf { BODYPART_COST[it]!! }) {
        return
    }

    val role: Role = when {
        creeps.count { it.memory.role == Role.HARVESTER } < 8 -> Role.HARVESTER

        creeps.count { it.memory.role == Role.UPGRADER } < 5 -> Role.UPGRADER

        creeps.count { it.memory.role == Role.REPAIRER } < 1 -> Role.REPAIRER

        spawn.room.find(FIND_MY_CONSTRUCTION_SITES).isNotEmpty() &&
                creeps.count { it.memory.role == Role.BUILDER } < 2 -> Role.BUILDER

        creeps.count { it.memory.role == Role.SWEEPER } < 1 -> Role.SWEEPER
        else -> return
    }

    val newName = "${role.name}_${Game.time}"
    baseBody = when (role) {
        Role.SWEEPER -> arrayOf(CARRY, CARRY, MOVE, MOVE)
        Role.REPAIRER -> arrayOf(WORK, CARRY, CARRY, MOVE)
        else -> baseBody
    }

    val code = spawn.spawnCreep(baseBody, newName, options {
        memory = jsObject<CreepMemory> {
            this.role = role
        }
    })

    when (code) {
        OK -> console.log("使用部件${baseBody}生成了新Creep:${newName}")
        ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { } // do nothing
        else -> console.log("无法解析错误代码$code")
    }
}