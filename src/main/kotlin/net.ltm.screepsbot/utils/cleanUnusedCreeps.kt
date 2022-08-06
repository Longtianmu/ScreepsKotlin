package net.ltm.screepsbot.utils

import screeps.api.*
import screeps.utils.isEmpty
import screeps.utils.unsafe.delete

fun cleanUnusedCreeps(creeps: Record<String, Creep>) {
    if (Game.creeps.isEmpty()) return  // this is needed because Memory.creeps is undefined

    for ((creepName, _) in Memory.creeps) {
        if (creeps[creepName] == null) {
            console.log("删除失效Creep:$creepName")
            delete(Memory.creeps[creepName])
        }
    }
}