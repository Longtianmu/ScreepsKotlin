package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.creepsLogic.*
import net.ltm.screepsbot.memory.numberOfCreeps
import net.ltm.screepsbot.memory.role
import screeps.api.*
import screeps.api.structures.StructureSpawn

fun gameLoop() {
    val mainSpawn: StructureSpawn = Game.spawns["MainHome"]!!

    houseKeeping(Game.creeps)

    mainSpawn.room.memory.numberOfCreeps = mainSpawn.room.find(FIND_CREEPS).count()

    spawnCreeps(Game.creeps.values, mainSpawn)

    val controller = mainSpawn.room.controller

    /*for ((_, room) in Game.rooms) {
        if (room.energyAvailable >= 550) {
            val name = "大型Creep_${Game.time}"
            mainSpawn.spawnCreep(
                arrayOf(
                    WORK,
                    WORK,
                    WORK,
                    CARRY,
                    MOVE,
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
    }*/

    for ((_, creep) in Game.creeps) {
        when (creep.memory.role) {
            Role.HARVESTER -> creep.harvest()
            Role.BUILDER -> creep.build()
            Role.UPGRADER -> creep.upgrade(controller!!)
            Role.SWEEPER -> creep.sweep()
            Role.REPAIRER -> creep.repairer()
            else -> creep.pause()
        }
    }
}




