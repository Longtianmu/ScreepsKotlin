package net.ltm.screepsbot.creepsLogic.roleLogic

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.memory.pause
import net.ltm.screepsbot.memory.role
import screeps.api.Creep

fun Creep.pause() {
    if (memory.pause < 10) {
        //blink slowly
        if (memory.pause % 3 != 0) say("\uD83D\uDEAC", true)
        memory.pause++
    } else {
        memory.pause = 0
        memory.role = Role.HARVESTER
    }
}