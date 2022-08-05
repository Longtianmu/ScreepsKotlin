package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.creepsLogic.*
import net.ltm.screepsbot.memory.numberOfCreeps
import net.ltm.screepsbot.memory.role
import screeps.api.*
import screeps.api.structures.StructureSpawn
import screeps.utils.unsafe.jsObject

fun gameLoop() {
    val mainSpawn: StructureSpawn = Game.spawns["MainHome"]!!

    houseKeeping(Game.creeps)

    mainSpawn.room.memory.numberOfCreeps = mainSpawn.room.find(FIND_CREEPS).count()

    spawnCreeps(Game.creeps.values, mainSpawn)

    val controller = mainSpawn.room.controller
    /*if ((controller != null) && (controller.level >= 2)) {
        when (controller.room.find(FIND_MY_STRUCTURES).count { it.structureType == STRUCTURE_EXTENSION }) {
            0 -> controller.room.createConstructionSite(29, 27, STRUCTURE_EXTENSION)
            1 -> controller.room.createConstructionSite(28, 27, STRUCTURE_EXTENSION)
            2 -> controller.room.createConstructionSite(27, 27, STRUCTURE_EXTENSION)
            3 -> controller.room.createConstructionSite(26, 27, STRUCTURE_EXTENSION)
            4 -> controller.room.createConstructionSite(25, 27, STRUCTURE_EXTENSION)
            5 -> controller.room.createConstructionSite(24, 27, STRUCTURE_EXTENSION)
            6 -> controller.room.createConstructionSite(23, 27, STRUCTURE_EXTENSION)
        }
    }*/

    for ((_, room) in Game.rooms) {
        if (room.energyAvailable >= 550) {
            val name = "大型Creeps_${Game.time}"
            mainSpawn.spawnCreep(
                arrayOf(
                    WORK,
                    WORK,
                    WORK,
                    WORK,
                    CARRY,
                    MOVE,
                    MOVE
                ),
                name,
                options {
                    memory = jsObject<CreepMemory> {
                        this.role = Role.HARVESTER
                    }
                }
            )
            console.log("生成了大型Creeps:$name")
        }
    }

    for ((_, creep) in Game.creeps) {
        when (creep.memory.role) {
            Role.HARVESTER -> creep.harvest()
            Role.BUILDER -> creep.build()
            Role.UPGRADER -> creep.upgrade(controller!!)
            Role.SWEEPER -> creep.sweep()
            else -> creep.pause()
        }
    }
}




