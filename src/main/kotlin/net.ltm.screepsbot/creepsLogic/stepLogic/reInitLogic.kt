package net.ltm.screepsbot.creepsLogic.stepLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.bruceChecker
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.memory.init
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.taskList
import screeps.api.Creep
import screeps.api.get

fun stepReInit(creep: Creep): StepReturnCode {
    val checkers = creep.memory.option[Step.RE_INIT.name]?.get("Checker")
        ?: return StepReturnCode.ERR_NEED_RESET
    val result = bruceChecker(checkers, creep)
    if (result) {
        creep.memory.init = false
        creep.memory.taskList = arrayOf()
    }
    return StepReturnCode.SKIP_TICK
}