package net.ltm.screepsbot.utils

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.memory.option
import screeps.api.*
import screeps.api.structures.StructureContainer
import screeps.utils.mutableRecordOf

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
    val containerCache = room.find(FIND_STRUCTURES)
        .filter { it.structureType == STRUCTURE_CONTAINER }
        .map { it.unsafeCast<StoreOwner>() }
        .filter { it.store.getFreeCapacity() > 0 }
        .sortedByDescending { it.store.getFreeCapacity() }
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
        .filter { it.store.getUsedCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getUsedCapacity(RESOURCE_ENERGY) }
    val containerCache = room.find(FIND_STRUCTURES)
        .filter { it.structureType == STRUCTURE_CONTAINER }
        .map { it.unsafeCast<StoreOwner>() }
        .filter { it.store.getUsedCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getUsedCapacity(RESOURCE_ENERGY) }

    id = if (room.storage != null && room.storage!!.store.getUsedCapacity(RESOURCE_ENERGY) > 0) {
        room.storage!!.id
    } else if (containerCache.isNotEmpty()) {
        containerCache[0].id
    } else if (spawnCache.isNotEmpty()) {
        spawnCache[0].id
    } else {
        "null"
    }
    return id
}

fun Creep.assignStepOption(step: Step, optionKey: String, optionValue: String, reset: Boolean = false) {
    if (reset || memory.option[step.name] == null)
        memory.option[step.name] = mutableRecordOf()
    memory.option[step.name]!![optionKey] = optionValue
}

/*fun Creep.forceStepMove(targetID: String) {
    memory.taskList = listOf(Step.MOVE.name).plus(memory.taskList).toTypedArray()
    memory.option[Step.MOVE.name] = mutableRecordOf()
    memory.option[Step.MOVE.name]!!["Target"] = targetID
}*/

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

fun <T> RoomPosition.lookInRange(type: LookConstant<T>, range: Int = 1): List<T> {
    if (Game.rooms[roomName] == null) return listOf()
    val ret = mutableListOf<T>()
    getPosInRange(range).forEach {
        val targets = Game.rooms[roomName]?.lookForAt(type, it.x, it.y)!!
        ret.addAll(targets)
    }
    return ret
}

fun getNearbyContainer(target: HasPosition): StructureContainer? {
    val look = target.pos.lookInRange(LOOK_STRUCTURES, 1).filter { it.structureType == STRUCTURE_CONTAINER }
    return look[0].unsafeCast<StructureContainer?>()
}

// 野蛮的
fun isSourceObject(target: Identifiable): Boolean {
    return target.unsafeCast<Source?>()?.ticksToRegeneration != null
            && target.unsafeCast<Source?>()?.energyCapacity != null
}