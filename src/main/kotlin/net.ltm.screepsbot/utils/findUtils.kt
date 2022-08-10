package net.ltm.screepsbot.utils

import screeps.api.*
import screeps.api.structures.Structure

fun Room.findStructure(type: StructureConstant): List<Structure> {
    return find(FIND_STRUCTURES)
        .filter { it.structureType == type }
}

fun Room.findGeneralStoreOwner(): List<StoreOwner?> {
    if (storage != null) return listOf(storage?.unsafeCast<StoreOwner>()) // 如果有storage，那就不考虑放到container里了
    return findStructure(STRUCTURE_CONTAINER)
        .map { it.unsafeCast<StoreOwner>() }
}

fun Room.findStructureNeedFills(): String {
    val towers = this.find(FIND_MY_STRUCTURES)
        .filter { it.structureType == STRUCTURE_TOWER }
        .map { it.unsafeCast<StoreOwner>() }
        .sortedBy { it.store.getUsedCapacity(RESOURCE_ENERGY) }
    return if (towers.isNotEmpty()) {
        towers.first().id
    } else {
        "null"
    }
}