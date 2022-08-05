package net.ltm.screepsbot.utils

import screeps.api.*

fun getNextTarget(room: Room): String {
    val id: String
    val spawnCache = room.find(FIND_MY_STRUCTURES)
        .filter { it.structureType == STRUCTURE_SPAWN }
        .map { it.unsafeCast<StoreOwner>() }
        .filter { it.store.getFreeCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getFreeCapacity(RESOURCE_ENERGY) }
    val extensionCache = room.find(FIND_MY_STRUCTURES)
        .filter { it.structureType == STRUCTURE_EXTENSION }
        .map { it.unsafeCast<StoreOwner>() }
        .filter { it.store.getFreeCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getFreeCapacity(RESOURCE_ENERGY) }
    val containerCache = room.find(FIND_MY_STRUCTURES)
        .filter { it.structureType == STRUCTURE_CONTAINER }
        .map { it.unsafeCast<StoreOwner>() }
        .filter { it.store.getFreeCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getFreeCapacity(RESOURCE_ENERGY) }
    id = if (spawnCache.isNotEmpty()) {
        spawnCache[0].id
    } else if (extensionCache.isNotEmpty()) {
        extensionCache[0].id
    } else if (room.storage != null) {
        room.storage!!.id
    } else if (containerCache.isNotEmpty()) {
        containerCache[0].id
    } else {
        "null"
    }
    return id
}

fun getUsableContainer(room: Room): String {
    val id: String
    val spawnCache = room.find(FIND_MY_STRUCTURES)
        .filter { it.structureType == STRUCTURE_SPAWN }
        .map { it.unsafeCast<StoreOwner>() }
        .sortedBy { it.store.getFreeCapacity(RESOURCE_ENERGY) }
    val containerCache = room.find(FIND_MY_STRUCTURES)
        .filter { it.structureType == STRUCTURE_CONTAINER }
        .map { it.unsafeCast<StoreOwner>() }
        .sortedBy { it.store.getFreeCapacity(RESOURCE_ENERGY) }
    id = if (containerCache.isNotEmpty()) {
        containerCache[0].id
    } else if (room.storage != null) {
        room.storage!!.id
    } else if (spawnCache.isNotEmpty()) {
        spawnCache[0].id
    } else {
        "null"
    }
    return id
}