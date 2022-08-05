package net.ltm.screepsbot.creepsLogic

import net.ltm.screepsbot.memory.from
import net.ltm.screepsbot.memory.to
import net.ltm.screepsbot.utils.findClosestNotEmpty
import net.ltm.screepsbot.utils.findGeneralStoreOwner
import net.ltm.screepsbot.utils.getNextTarget
import screeps.api.*

fun Creep.wat() {
    val from = Game.getObjectById<StoreOwner>(memory.from)
    val to = Game.getObjectById<StoreOwner>(memory.to)
    if (from == null || to == null) return
    if (store.getFreeCapacity() > 0) {
        if (!pos.isNearTo(from.pos)) moveTo(from)
        val ret = from.store.keys.firstOrNull { withdraw(from, it) == OK }
        if (ret == null) memory.from = ""
    } else {
        if (!pos.isNearTo(to.pos)) moveTo(to)
        val ret = store.keys.firstOrNull { transfer(to, it) == OK }
        if (ret == null) memory.to = ""
    }
}

fun Creep.sweep(assignedRoom: Room = this.room) {
    // withdraw(): Ruin Tombstone
    // pickup(): Resource
    if (memory.from == "") {
        val ruins = room.find(FIND_RUINS)
            .filter { it.store.getUsedCapacity() > 0 }

        if (ruins.isNotEmpty())
            memory.from = findClosestNotEmpty(ruins.toTypedArray()).id
        else {
            val tombstones = room.find(FIND_TOMBSTONES)
                .filter { it.store.getUsedCapacity() > 0 }

            if (tombstones.isNotEmpty())
                memory.from = findClosestNotEmpty(tombstones.toTypedArray()).id
        }
    }

    if (memory.to == "") {
        if (store.getUsedCapacity() - store.getUsedCapacity(RESOURCE_ENERGY)!! > 0) {
            memory.to = findClosestNotEmpty(
                room.findGeneralStoreOwner()
                    .map { it.unsafeCast<StoreOwner>() }
                    .filter { it.store.getFreeCapacity() > 0 }
                    .toTypedArray()
            ).id
        } else {
            val ret = getNextTarget(room)
            memory.to = if (ret == "null") room.storage?.id.toString() else ret
        }
    }
    wat()
}