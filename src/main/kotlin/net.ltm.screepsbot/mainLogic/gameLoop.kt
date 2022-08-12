package net.ltm.screepsbot.mainLogic

import net.ltm.screepsbot.mainLogic.creepLoop.creepLoop
import net.ltm.screepsbot.mainLogic.roomLoop.roomLoop
import net.ltm.screepsbot.utils.cleanUnusedThings
import screeps.api.Game
import screeps.api.values

fun gameLoop() {
    cleanUnusedThings()

    val rooms = Game.rooms.values
    val roomsOwned = rooms.filter { it.controller?.my == true }

    creepLoop()
    roomLoop(roomsOwned)


    try {
        if (Game.cpu.bucket == 10000) {
            Game.cpu.generatePixel()
        }
    } catch (_: Exception) {

    }
}




