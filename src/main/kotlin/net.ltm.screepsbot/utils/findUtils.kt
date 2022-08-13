package net.ltm.screepsbot.utils

import screeps.api.*
import screeps.api.structures.Structure
import screeps.api.structures.StructureContainer
import screeps.api.structures.StructureTower

inline fun <reified T : Structure> Room.findStructure(): List<T> {
    return find(FIND_STRUCTURES)
        .filterIsInstance<T>()
}

inline fun <reified T : Structure> Room.findMyStructure(): List<T> {
    return find(FIND_MY_STRUCTURES)
        .filterIsInstance<T>()
}

fun Room.findGeneralStoreOwner(): List<StoreOwner?> {
    if (storage != null) return listOf(storage?.unsafeCast<StoreOwner>()) // 如果有storage，那就不考虑放到container里了
    return findStructure<StructureContainer>()
        .map { it.unsafeCast<StoreOwner>() }
}

fun Room.findStructureNeedFills(): String? {
    val towers = this.find(FIND_MY_STRUCTURES)
        .filterIsInstance<StructureTower>()
        .sortedBy { it.store.getUsedCapacity(RESOURCE_ENERGY) }
    return if (towers.isNotEmpty()) {
        towers.first().id
    } else {
        null
    }
}