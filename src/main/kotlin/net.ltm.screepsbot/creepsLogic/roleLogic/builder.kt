package net.ltm.screepsbot.creepsLogic.roleLogic

import net.ltm.screepsbot.memory.building
import net.ltm.screepsbot.memory.movePos
import net.ltm.screepsbot.utils.getUsableContainer
import screeps.api.*

fun Creep.build(assignedRoom: Room = this.room) {
    if (memory.building && store[RESOURCE_ENERGY] == 0) {
        memory.building = false
        say("🔄获取资源", true)
    }
    if (!memory.building && store[RESOURCE_ENERGY] == store.getCapacity()) {
        memory.building = true
        say("🚧开始建造", true)
    }

    if (memory.building) {
        val targets = assignedRoom.find(FIND_MY_CONSTRUCTION_SITES)
        if (targets.isNotEmpty()) {
            if (build(targets[0]) == ERR_NOT_IN_RANGE) {
                moveTo(targets[0].pos)
            }
        }
    } else {
        if (memory.movePos == "WTF") {
            val cache = getUsableContainer(assignedRoom)
            if (cache != "null") {
                memory.movePos = cache
            }
        } else {
            val temp = Game.getObjectById<StoreOwner>(memory.movePos)!!
            when (withdraw(temp, RESOURCE_ENERGY)) {
                ERR_NOT_IN_RANGE -> {
                    moveTo(temp)
                }

                ERR_NOT_ENOUGH_RESOURCES -> {
                    memory.movePos = "WTF"
                }
            }
        }
    }
}