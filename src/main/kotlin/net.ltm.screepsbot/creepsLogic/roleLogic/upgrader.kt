package net.ltm.screepsbot.creepsLogic.roleLogic

import net.ltm.screepsbot.memory.movePos
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.*
import screeps.api.structures.Structure
import screeps.api.structures.StructureController

fun Creep.upgrade(controller: StructureController) {
    when (upgradeController(controller)) {
        ERR_NOT_ENOUGH_ENERGY -> {
            if (memory.movePos == "WTF") {
                val storage = getUsableContainer(this.room)
                if (storage == "null") {
                    memory.movePos = pos.findClosestByPath(FIND_SOURCES)?.id.toString()
                } else {
                    memory.movePos = storage
                }
            }
            when (val temp = Game.getObjectById<Identifiable>(memory.movePos)) {
                is Structure -> {
                    when (withdraw(temp.unsafeCast<StoreOwner>(), RESOURCE_ENERGY)) {
                        ERR_NOT_ENOUGH_ENERGY -> {
                            memory.movePos = "WTF"
                        }

                        ERR_NOT_IN_RANGE -> {
                            moveTo(temp)
                        }
                    }
                }

                is Source -> {
                    if (harvest(temp) == ERR_NOT_IN_RANGE) {
                        if (moveTo(temp) == ERR_NO_PATH) {
                            val cache = room.find(FIND_SOURCES).filter {
                                it.id != temp.id
                            }
                            if (cache.isNotEmpty()) {
                                memory.movePos = cache[0].id
                            }
                        }
                    }
                }
            }
        }

        ERR_NOT_IN_RANGE -> {
            moveTo(controller.pos)
        }
    }
}