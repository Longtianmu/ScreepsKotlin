package net.ltm.screepsbot.profiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import screeps.api.*

abstract class Profile {
    abstract val tag: BodyPartConstant
    abstract val preTask: MutableList<String>
    abstract val taskLoop: MutableList<String>
    abstract fun initGenerator(creep: Creep): GeneratorReturnCode
    abstract fun loopGenerator(creep: Creep): GeneratorReturnCode
}

abstract class HarvesterProfile : Profile() {
    override val tag = WORK
    override val preTask = mutableListOf(Step.MOVE.name)
    override val taskLoop = mutableListOf(Step.HARVEST.name, Step.TRANSFER.name)
}

abstract class UpgraderProfile : Profile() {
    override val tag = WORK
    override val preTask = mutableListOf(Step.WITHDRAW.name)
    override val taskLoop = mutableListOf(Step.UPGRADE_CONTROLLER.name, Step.RE_INIT.name)
}

abstract class CarrierProfile : Profile() {
    override val tag = CARRY
    override val preTask = mutableListOf(Step.WITHDRAW.name)
    override val taskLoop = mutableListOf(Step.TRANSFER.name, Step.RE_INIT.name)
}

abstract class BuilderProfile : Profile() {
    override val tag = WORK
    override val preTask = mutableListOf(Step.WITHDRAW.name)
    override val taskLoop = mutableListOf(Step.BUILD.name, Step.RE_INIT.name)
}

abstract class RepairerProfile : Profile() {
    override val tag = WORK
    override val preTask = mutableListOf(Step.WITHDRAW.name)
    override val taskLoop = mutableListOf(Step.REPAIR.name, Step.RE_INIT.name)
}

abstract class ClaimerProfile : Profile() {
    override val tag = CLAIM
    override val preTask = mutableListOf(Step.MOVE.name)
    override val taskLoop = mutableListOf(Step.CLAIM_CONTROLLER.name)
}
/*
abstract class StarterHarvester : Profile() {
    override val tag = WORK
    override val preTask = mutableListOf(Step.MOVE.name)
    override val taskLoop = mutableListOf(Step.HARVEST.name, Step.TRANSFER.name, Step.BUILD.name)
}

abstract class SweeperProfile : Profile() {
    override val preTask = mutableListOf<String>()
    override val taskLoop = mutableListOf(Step.PICKUP.name, Step.TRANSFER.name)
}*/