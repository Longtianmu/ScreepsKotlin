package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.creepsLogic.roleLogic.*
import net.ltm.screepsbot.creepsLogic.tick
import net.ltm.screepsbot.memory.maxCountMap
import net.ltm.screepsbot.memory.role
import net.ltm.screepsbot.memory.roleClass
import net.ltm.screepsbot.utils.cleanUnusedCreeps
import screeps.api.*
import screeps.api.structures.StructureTower

fun gameLoop() {
    val rooms = Game.rooms.values
    cleanUnusedCreeps(Game.creeps)
    for (room in rooms) {
        if (room.controller?.my != true) continue
        val roomCreeps = Game.creeps.values.filter { it.room == room }
        val currentController = room.controller
        val spawns = room.find(FIND_MY_SPAWNS)
        val towers = room.find(FIND_MY_STRUCTURES)
            .filter { it.structureType == STRUCTURE_TOWER }
            .map { it.unsafeCast<StructureTower>() }
        if (room.memory.maxCountMap.size != maxCountMap.size) {
            room.memory.maxCountMap = maxCountMap
        }
        for (spawn in spawns) {
            spawnCreeps(roomCreeps, spawn)
        }
        for (creep in roomCreeps) {
            if (creep.spawning) {
                continue
            }
            when (creep.memory.roleClass) {
                "null" -> when (creep.memory.role) {
                    Role.HARVESTER -> creep.harvest()
                    Role.BUILDER -> creep.build()
                    Role.UPGRADER -> creep.upgrade(currentController!!)
                    Role.SWEEPER -> creep.sweep()
                    Role.REPAIRER -> creep.repairer()
                    else -> creep.pause()
                }

                else -> when (creep.tick()) {
                    TickReturnCode.REWORK -> {
                        creep.tick()
                    }

                    TickReturnCode.OK -> {

                    }
                }
            }
        }
        for (tower in towers) {
            val creepEnemy = room.find(FIND_HOSTILE_CREEPS).sortedByDescending { it.hits }
            val powerCreepEnemy = room.find(FIND_HOSTILE_POWER_CREEPS).sortedByDescending { it.hits }
            val creep = roomCreeps.filter { it.hits < it.hitsMax }.sortedBy { it.hits }
            val powerCreep = room.find(FIND_MY_POWER_CREEPS).filter { it.hits < it.hitsMax }.sortedBy { it.hits }
            val brokenBuilds = room.find(FIND_STRUCTURES).filter { it.hits < it.hitsMax }.sortedBy { it.hits }
            if (powerCreepEnemy.isNotEmpty()) {
                tower.attack(powerCreepEnemy.first())
            } else if (creepEnemy.isNotEmpty()) {
                tower.attack(creepEnemy.first())
            } else if (powerCreep.isNotEmpty()) {
                tower.heal(powerCreep.first())
            } else if (creep.isNotEmpty()) {
                tower.heal(creep.first())
            } else if (brokenBuilds.isNotEmpty()) {
                val it = brokenBuilds.first()
                if (it.structureType in listOf(STRUCTURE_WALL, STRUCTURE_RAMPART)) {
                    if (Game.time % 10 == 0) {
                        tower.repair(it)
                    }
                } else {
                    tower.repair(it)
                }
            }
        }
    }
    if (Game.cpu.bucket == 10000) {
        Game.cpu.generatePixel()
    }
}




