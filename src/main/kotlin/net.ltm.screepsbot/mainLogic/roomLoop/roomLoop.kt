package net.ltm.screepsbot.mainLogic.roomLoop

import net.ltm.screepsbot.mainLogic.spawnLoop.spawnLoop
import screeps.api.Game
import screeps.api.Room
import screeps.api.values

fun roomLoop(roomsOwned: List<Room>) {
    for (room in roomsOwned) {
        val roomCreeps = Game.creeps.values.filter { it.room == room }
        spawnLoop(room, roomCreeps)
        towerLoop(room, roomCreeps)
    }
}
