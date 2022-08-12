package net.ltm.screepsbot.utils.creepUtils

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.memory.option
import screeps.api.Creep
import screeps.api.get
import screeps.api.set
import screeps.utils.mutableRecordOf

fun Creep.assignStepOption(step: Step, optionKey: String, optionValue: String, reset: Boolean = false) {
    if (reset || memory.option[step.name] == null)
        memory.option[step.name] = mutableRecordOf()
    memory.option[step.name]!![optionKey] = optionValue
}