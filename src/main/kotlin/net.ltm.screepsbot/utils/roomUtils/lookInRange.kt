package net.ltm.screepsbot.utils.roomUtils

import screeps.api.Game
import screeps.api.LookConstant
import screeps.api.RoomPosition
import screeps.api.get

fun <T> RoomPosition.lookInRange(type: LookConstant<T>, range: Int = 1): List<T> {
    if (Game.rooms[roomName] == null) return listOf()
    val ret = mutableListOf<T>()
    getPosInRange(range).forEach {
        val targets = Game.rooms[roomName]?.lookForAt(type, it.x, it.y)!!
        ret.addAll(targets)
    }
    return ret
}