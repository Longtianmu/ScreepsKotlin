package net.ltm.screepsbot.mainLogic.creepLoop

import net.ltm.screepsbot.constant.returnCode.TickReturnCode
import net.ltm.screepsbot.creepsLogic.tickLogic.tick
import net.ltm.screepsbot.memory.init
import screeps.api.Game
import screeps.api.component1
import screeps.api.component2
import screeps.api.iterator

fun creepLoop() {
    for ((_, creep) in Game.creeps) {
        when (creep.tick()) {
            TickReturnCode.REWORK -> {
                creep.tick()
            }

            TickReturnCode.OK -> {}

            TickReturnCode.FAILED -> {
                println("${creep.name}发生了Tick错误\n内存为${JSON.stringify(creep.memory)}")
                creep.memory.init = false
            }
        }
    }
}