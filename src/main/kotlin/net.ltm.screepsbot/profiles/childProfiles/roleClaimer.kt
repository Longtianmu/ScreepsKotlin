package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.ClaimerProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import screeps.api.Creep
import screeps.api.Game
import screeps.api.get

class RoleClaimer : ClaimerProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        Game.flags[creep.name + "_flagTarget"]?.let {
            creep.assignStepOption(
                Step.MOVE, "Target",
                "${it.pos.x},${it.pos.y},${it.pos.roomName}"
            )
            return GeneratorReturnCode.OK
        }
        return GeneratorReturnCode.NO_TARGET
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        creep.room.controller?.let {
            creep.assignStepOption(Step.CLAIM_CONTROLLER, "Target", it.id)
            return GeneratorReturnCode.OK
        }
        return GeneratorReturnCode.NO_TARGET
    }
}