package net.ltm.screepsbot.mainLogic.roomLoop

import screeps.api.Game
import screeps.api.Room
import screeps.api.values

fun roomLoop(roomsOwned: List<Room>) {
    for (room in roomsOwned) {
        val roomCreeps = Game.creeps.values.filter { it.room == room }
        try {
            spawnLoop(room, roomCreeps)
        } catch (e: Exception) {
            println("Spawn Failed")
            e.printStackTrace()
        }

        towerLoop(room, roomCreeps)
    }
}
