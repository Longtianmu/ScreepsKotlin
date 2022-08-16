package net.ltm.screepsbot.utils

import screeps.api.*
import screeps.utils.unsafe.delete

fun cleanUnusedThings() {
    for ((roomsName, _) in Memory.rooms) {
        if (Game.rooms[roomsName]?.controller?.my == false || Game.rooms[roomsName] == null) {
            console.log("删除失效Room:$roomsName")
            delete(Memory.rooms[roomsName])
            delete(Memory.rooms.asDynamic().stats.rooms[roomsName])
        }
    }

    for ((creepName, _) in Memory.creeps) {
        if (Game.creeps[creepName] == null) {
            console.log("删除失效Creep:$creepName")
            delete(Memory.creeps[creepName])
        }
    }
}