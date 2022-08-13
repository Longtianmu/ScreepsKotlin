package net.ltm.screepsbot.utils.creepUtils

import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY

fun Creep.hasNoEnergy(): Boolean {
    return store.getUsedCapacity(RESOURCE_ENERGY) == 0
}