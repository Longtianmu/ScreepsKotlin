package net.ltm.screepsbot.profiles

import net.ltm.screepsbot.memory.roleClass
import screeps.api.Creep

fun Creep.assign(role: Profile) {
    memory.roleClass = role::class.simpleName.toString()
}