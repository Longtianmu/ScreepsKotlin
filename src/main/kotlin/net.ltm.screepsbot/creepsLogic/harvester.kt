package net.ltm.screepsbot.creepsLogic

import net.ltm.screepsbot.memory.movePos
import net.ltm.screepsbot.utils.getNextTarget
import screeps.api.*
import screeps.api.structures.Structure

fun Creep.harvest(fromRoom: Room = this.room, toRoom: Room = this.room) {
    if (memory.movePos == "WTF") {
        memory.movePos = room.find(FIND_SOURCES).random().id
    } else {
        when (val temp = Game.getObjectById<Identifiable>(memory.movePos)) {
            is Structure -> {
                when (transfer(temp.unsafeCast<StoreOwner>(), RESOURCE_ENERGY)) {
                    ERR_FULL -> {
                        val cache = getNextTarget(this.room)
                        if (cache != "null") {
                            memory.movePos = cache
                        }
                    }

                    ERR_NOT_IN_RANGE -> {
                        moveTo(temp)
                    }

                    OK -> {
                        memory.movePos = room.find(FIND_SOURCES).random().id
                    }
                }
            }

            is Source -> {
                var transferOrNot = false
                if (store.getFreeCapacity(RESOURCE_ENERGY) == 0) {
                    transferOrNot = true
                } else {
                    when (harvest(temp)) {
                        ERR_NOT_IN_RANGE -> {
                            if (moveTo(temp) == ERR_NO_PATH) {
                                val cache = room.find(FIND_SOURCES).filter {
                                    it.id != temp.id
                                }
                                if (cache.isNotEmpty()) {
                                    memory.movePos = cache[0].id
                                }
                            }
                        }

                        ERR_NOT_ENOUGH_RESOURCES -> {
                            transferOrNot = true
                        }
                    }
                }
                if (transferOrNot) {
                    val cache = getNextTarget(this.room)
                    if (cache != "null") {
                        memory.movePos = cache
                    }
                }
            }
        }
    }
}