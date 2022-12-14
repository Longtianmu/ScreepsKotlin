package net.ltm.screepsbot.utils

import screeps.api.Creep
import screeps.api.HasPosition

fun <T : HasPosition> Creep.findClosest(roomObjects: Collection<T>): T? {
    var closest: T? = null
    var minDistance = Int.MAX_VALUE
    for (roomObject in roomObjects) {
        val dist = (roomObject.pos.x - this.pos.x) * (roomObject.pos.x - this.pos.x) +
                (roomObject.pos.y - this.pos.y) * (roomObject.pos.y - this.pos.y)

        if (dist < minDistance) {
            minDistance = dist
            closest = roomObject
        }
    }
    return closest
}

fun <T : HasPosition> Creep.findClosestNotEmpty(roomObjects: Array<out T>): T {
    require(roomObjects.isNotEmpty())
    return findClosest(roomObjects.asList())!!
}