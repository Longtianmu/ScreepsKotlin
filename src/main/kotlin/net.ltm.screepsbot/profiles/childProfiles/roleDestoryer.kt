package net.ltm.screepsbot.profiles.childProfiles

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.profiles.DestroyerProfile
import net.ltm.screepsbot.utils.creepUtils.assignStepOption
import screeps.api.Creep
import screeps.api.Game
import screeps.api.LOOK_STRUCTURES
import screeps.api.values

class RoleDestroyer : DestroyerProfile() {
    override fun initGenerator(creep: Creep): GeneratorReturnCode {
        Game.flags.values.firstOrNull { it.name.contains("Destroy") }?.let {
            creep.assignStepOption(
                Step.MOVE, "Target",
                "${it.pos.x},${it.pos.y},${it.pos.roomName}"
            )
            return GeneratorReturnCode.OK
        }
        return GeneratorReturnCode.NO_TARGET
    }

    override fun loopGenerator(creep: Creep): GeneratorReturnCode {
        Game.flags.values.firstOrNull { it.name.contains("Destroy") }?.let {
            val found = creep.room.lookForAt(LOOK_STRUCTURES, it.pos.x, it.pos.y)
            if (found!!.isNotEmpty()) {
                creep.assignStepOption(
                    Step.ATTACK, "Target",
                    found[0].id
                )
                return GeneratorReturnCode.OK
            }
        }
        return GeneratorReturnCode.NO_TARGET
    }
}