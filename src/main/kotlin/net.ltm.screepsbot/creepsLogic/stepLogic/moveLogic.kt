package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.TickReturnCode
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.taskRetry
import screeps.api.*

fun stepMove(creep: Creep): TickReturnCode {
    val targetID = creep.memory.option[Step.MOVE.name]?.get("Target")
    return if (targetID.isNullOrEmpty()) {
        console.log("stepMove出现参数错误，Target为空")
        TickReturnCode.ERR_NEED_RESET
    } else {
        val target = Game.getObjectById<Identifiable>(targetID).unsafeCast<HasPosition>()
        if (creep.moveTo(target) == ERR_NO_PATH) {
            creep.memory.taskRetry++
        }
        TickReturnCode.OK
    }
}