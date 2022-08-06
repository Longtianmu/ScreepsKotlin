package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.creepsLogic.*
import net.ltm.screepsbot.memory.role
import net.ltm.screepsbot.utils.cleanUnusedCreeps
import screeps.api.FIND_MY_SPAWNS
import screeps.api.Game
import screeps.api.values

fun gameLoop() {
    val rooms = Game.rooms.values
    cleanUnusedCreeps(Game.creeps)
    for (room in rooms) {
        if (room.controller?.my != true) continue
        val roomCreeps = Game.creeps.values.filter { it.room == room }
        val currentController = room.controller
        val spawns = room.find(FIND_MY_SPAWNS)
        for (spawn in spawns) {
            spawnCreeps(roomCreeps.toTypedArray(), spawn)
        }
        for (creep in roomCreeps) {
            when (creep.memory.role) {
                Role.HARVESTER -> creep.harvest()
                Role.BUILDER -> creep.build()
                Role.UPGRADER -> creep.upgrade(currentController!!)
                Role.SWEEPER -> creep.sweep()
                Role.REPAIRER -> creep.repairer()
                else -> creep.pause()
            }
        }
    }
}




