package net.ltm.screepsbot.profiles

import net.ltm.screepsbot.constant.Step
import screeps.api.Creep

abstract class Profile {
    abstract val preTask: MutableList<String>
    abstract val taskLoop: MutableList<String>
    abstract fun initGenerator(creep: Creep)
    abstract fun loopGenerator(creep: Creep)
}

abstract class HarvesterProfile : Profile() {
    override val preTask = mutableListOf(Step.MOVE.name)
    override val taskLoop = mutableListOf(Step.HARVEST.name)
}

abstract class BuilderProfile : Profile() {
    override val preTask = mutableListOf<String>()
    override val taskLoop = mutableListOf(Step.WITHDRAW.name, Step.BUILD.name)
}