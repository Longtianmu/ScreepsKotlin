package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.memory.role
import screeps.api.*
import screeps.api.structures.StructureSpawn
import screeps.utils.unsafe.jsObject

fun spawnCreeps(
    creeps: Array<Creep>,
    spawn: StructureSpawn
) {
    var baseBody = arrayOf<BodyPartConstant>(WORK, CARRY, MOVE)
    if (spawn.room.energyAvailable < baseBody.sumOf { BODYPART_COST[it]!! }) {
        return
    }

    val role: Role = when {
        creeps.count { it.memory.role == Role.HARVESTER } < 8 -> Role.HARVESTER

        creeps.count { it.memory.role == Role.UPGRADER } < 2 -> Role.UPGRADER

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
        memory = jsObject<CreepMemory> { this.role = role }
    })

    when (code) {
        OK -> console.log("使用部件${baseBody}生成了新Creep:${newName}")
        ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { } // do nothing
        else -> console.log("无法解析错误代码$code")
    }
}