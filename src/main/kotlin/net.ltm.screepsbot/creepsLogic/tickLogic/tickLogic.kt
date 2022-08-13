package net.ltm.screepsbot.creepsLogic.tickLogic

import net.ltm.screepsbot.constant.returnCode.InitialReturnCode
import net.ltm.screepsbot.constant.returnCode.TickReturnCode
import net.ltm.screepsbot.memory.roleClass
import net.ltm.screepsbot.memory.taskList
import screeps.api.Creep

fun Creep.tick(): TickReturnCode {
    if (this.spawning) {
        return TickReturnCode.OK
    }
    if (memory.roleClass.isEmpty()) {
        return TickReturnCode.FAILED
    }

    if (memory.taskList.isEmpty()) {
        return when (init()) {
            InitialReturnCode.FAILED -> {
                TickReturnCode.FAILED
            }

            InitialReturnCode.OK -> {
                TickReturnCode.REWORK
            }

            InitialReturnCode.SKIP -> {
                TickReturnCode.OK
            }
        }
    }

    val currentTask = memory.taskList.first()
    return stepReturnCodeProcessor(this, stepDetector(this, currentTask), currentTask)
}
