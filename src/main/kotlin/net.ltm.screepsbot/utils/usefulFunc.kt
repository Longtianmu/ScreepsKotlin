package net.ltm.screepsbot.utils

import net.ltm.screepsbot.utils.roomUtils.lookInRange
import screeps.api.*
import screeps.api.structures.StructureContainer
import screeps.api.structures.StructureExtension
import screeps.api.structures.StructureLink
import screeps.api.structures.StructureSpawn

fun getNextTarget(room: Room): String? {
    val spawnCache = room.findMyStructure<StructureSpawn>()
        .filter { it.store.getFreeCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getFreeCapacity(RESOURCE_ENERGY) }
    val extensionCache = room.findMyStructure<StructureExtension>()
        .filter { it.store.getFreeCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getFreeCapacity(RESOURCE_ENERGY) }
    val linkCache = room.findMyStructure<StructureLink>()
        .filter {
            it.pos.lookInRange(LOOK_SOURCES, 3).isNotEmpty()
                    && it.store.getFreeCapacity(RESOURCE_ENERGY) > 0
        }
    val containerCache = room.findMyStructure<StructureContainer>()
        .filter { it.store.getFreeCapacity() > 0 }
        .sortedByDescending { it.store.getFreeCapacity() }
    val target = if (spawnCache.isNotEmpty()) {
        spawnCache.first().id
    } else if (extensionCache.isNotEmpty()) {
        extensionCache.random().id
    } else if (linkCache.isNotEmpty()) {
        linkCache.first().id
    } else if (room.storage != null) {
        room.storage!!.id
    } else if (containerCache.isNotEmpty()) {
        containerCache.first().id
    } else {
        null
    }
    return target
}

fun getUsableContainer(room: Room): String? {
    val spawnCache = room.find(FIND_MY_STRUCTURES)
        .filterIsInstance<StructureSpawn>()
        .filter { it.store.getUsedCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getUsedCapacity(RESOURCE_ENERGY) }
    val containerCache = room.find(FIND_STRUCTURES)
        .filterIsInstance<StructureContainer>()
        .filter { it.store.getUsedCapacity(RESOURCE_ENERGY) > 0 }
        .sortedByDescending { it.store.getUsedCapacity(RESOURCE_ENERGY) }

    return if (room.storage != null && room.storage!!.store.getUsedCapacity(RESOURCE_ENERGY) > 0) {
        room.storage!!.id
    } else if (containerCache.isNotEmpty()) {
        containerCache.first().id
    } else if (spawnCache.isNotEmpty()) {
        spawnCache.first().id
    } else {
        null
    }
}

/*fun Creep.forceStepMove(targetID: String) {
    memory.taskList = listOf(Step.MOVE.name).plus(memory.taskList).toTypedArray()
    memory.option[Step.MOVE.name] = mutableRecordOf()
    memory.option[Step.MOVE.name]!!["Target"] = targetID
}*/

fun getNearbyContainer(target: HasPosition): StructureContainer? {
    val look = target.pos.lookInRange(LOOK_STRUCTURES, 1).filterIsInstance<StructureContainer>()
    return look.firstOrNull()
}

// 野蛮的
fun isSourceObject(target: Identifiable): Boolean {
    return target.unsafeCast<Source?>()?.ticksToRegeneration != null
            && target.unsafeCast<Source?>()?.energyCapacity != null
}