package net.ltm.screepsbot.mainLogic.roomLoop

import net.ltm.screepsbot.utils.findMyStructure
import screeps.api.*
import screeps.api.structures.StructureTower

fun towerLoop(room: Room, roomCreeps: List<Creep>) {
    val towers = room.findMyStructure<StructureTower>()

    if (towers.isNotEmpty()) {
        val creepEnemy = room.find(FIND_HOSTILE_CREEPS).sortedByDescending { it.hits }
        val powerCreepEnemy = room.find(FIND_HOSTILE_POWER_CREEPS).sortedByDescending { it.hits }

        val creep = roomCreeps.filter { it.hits < it.hitsMax }.sortedBy { it.hits }
        val powerCreep = room.find(FIND_MY_POWER_CREEPS).filter { it.hits < it.hitsMax }.sortedBy { it.hits }

        val brokenBuilds = room.find(FIND_STRUCTURES).filter { it.hits < it.hitsMax }.sortedBy { it.hits }

        for (tower in towers) {
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
                    if (Game.time % 5 == 0) {
                        tower.repair(it)
                    }
                } else {
                    tower.repair(it)
                }
            }
        }
    }
}