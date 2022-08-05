package net.ltm.screepsbot.utils

import screeps.api.*
import screeps.api.structures.Structure

fun Room.findStructure(type: StructureConstant): List<Structure> {
    return find(FIND_STRUCTURES)
        .filter { it.structureType == type }
}

fun Room.findGeneralStoreOwner(): Array<StoreOwner?> {
    var ret: Array<StoreOwner?> = arrayOf(storage?.unsafeCast<StoreOwner>())
    if (storage != null) return ret // 如果有storage，那就不考虑放到container里了
    val containers = findStructure(STRUCTURE_CONTAINER)
        .map { it.unsafeCast<StoreOwner>() }
    if (containers.isNotEmpty())
        ret += containers
    return ret
}