package net.ltm.screepsbot.mainLogic.spawnLoop

import net.ltm.screepsbot.memory.maxCountMap
import net.ltm.screepsbot.memory.maxWorkCountMap
import screeps.api.Creep
import screeps.api.FIND_MY_SPAWNS
import screeps.api.Room
import screeps.api.size

fun spawnLoop(room: Room, roomCreeps: List<Creep>) {
    if (room.memory.maxCountMap.size != maxCountMap.size) {
        room.memory.maxCountMap = maxCountMap
    }
    if (room.memory.maxWorkCountMap.size != maxWorkCountMap.size) {
        room.memory.maxWorkCountMap = maxWorkCountMap
    }

    val spawns = room.find(FIND_MY_SPAWNS)
    for (spawn in spawns) {
        if (spawn.spawning != null) {
            continue
        }
        spawnCreeps(spawn, roomCreeps)
    }
}