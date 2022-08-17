package net.ltm.screepsbot.mainLogic.roomLoop

import net.ltm.screepsbot.utils.findMyStructure
import net.ltm.screepsbot.utils.roomUtils.lookInRange
import screeps.api.LOOK_SOURCES
import screeps.api.RESOURCE_ENERGY
import screeps.api.Room
import screeps.api.structures.StructureLink

fun linksLoop(room: Room) {
    val links = room.findMyStructure<StructureLink>()
    val sourceLink = links.filter { it.pos.lookInRange(LOOK_SOURCES, 3).isNotEmpty() }
    val costLink = (links.minus(sourceLink.toSet()))
        .filter { it.store.getFreeCapacity(RESOURCE_ENERGY)!! > 0 }
        .sortedBy { it.store.getUsedCapacity(RESOURCE_ENERGY) }
    for (i in sourceLink) {
        if (i.cooldown == 0) {
            i.transferEnergy(costLink.firstOrNull() ?: continue)
        }
    }
}