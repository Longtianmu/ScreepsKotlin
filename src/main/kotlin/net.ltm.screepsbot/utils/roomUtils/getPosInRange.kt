package net.ltm.screepsbot.utils.roomUtils

import screeps.api.RoomPosition

fun RoomPosition.getPosInRange(range: Int): List<RoomPosition> {
    val ret = mutableListOf<RoomPosition>()
    for (x_shift in (-range..range)) {
        if (x_shift + x < 0 || x_shift + x > 49) continue
        for (y_shift in (-range..range)) {
            if (y_shift + y < 0 || y_shift + y > 49) continue
            ret.add(RoomPosition(x_shift + x, y_shift + y, roomName))
        }
    }
    return ret
}