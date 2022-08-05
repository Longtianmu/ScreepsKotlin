package net.ltm.screepsbot.creepsLogic

import net.ltm.screepsbot.utils.findClosest
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.*

fun Creep.repairer() {
    if (store.getUsedCapacity() == 0) {
        val target = Game.getObjectById<StoreOwner>(getUsableContainer(this.room))
        if (withdraw(target!!, RESOURCE_ENERGY) == ERR_NOT_IN_RANGE) {
            moveTo(target)
        }
    } else {
        val target = findClosest(
            room.find(FIND_STRUCTURES)
                .filter { it.structureType in listOf(STRUCTURE_ROAD, STRUCTURE_CONTAINER) }
                .filter { it.hits < it.hitsMax }
                .toTypedArray()
        )
        if (target!=null && repair(target) == ERR_NOT_IN_RANGE) {
            moveTo(target)
        }
    }
}