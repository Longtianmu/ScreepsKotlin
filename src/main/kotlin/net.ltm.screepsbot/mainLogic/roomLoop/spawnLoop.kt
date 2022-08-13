package net.ltm.screepsbot.mainLogic.roomLoop

import net.ltm.screepsbot.mainLogic.spawnLoop.spawnCreeps
import net.ltm.screepsbot.memory.maxCountMap
import screeps.api.Creep
import screeps.api.FIND_MY_SPAWNS
import screeps.api.Room
import screeps.api.size

fun spawnLoop(room: Room, roomCreeps: List<Creep>) {
    if (room.memory.maxCountMap.size != maxCountMap.size) {
        room.memory.maxCountMap = maxCountMap
    }

    val spawns = room.find(FIND_MY_SPAWNS)

    for (spawn in spawns) {
        spawnCreeps(roomCreeps, spawn)
    }
}