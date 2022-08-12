package net.ltm.screepsbot.constant

import net.ltm.screepsbot.constant.returnCode.Checkers
import net.ltm.screepsbot.profiles.Profile
import net.ltm.screepsbot.profiles.childProfiles.*
import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY

val workRange = mapOf(
    Pair(Step.HARVEST.name, 1),
    Pair(Step.TRANSFER.name, 1),
    Pair(Step.WITHDRAW.name, 1),
    Pair(Step.BUILD.name, 3),
    Pair(Step.PICKUP.name, 1),
    Pair(Step.UPGRADE_CONTROLLER.name, 3),
    Pair(Step.REPAIR.name, 3)
)

fun bruceReflect(roleClassName: String): Profile? {
    try {
        return when (roleClassName) {
            "RoleHarvester1" -> RoleHarvester1()
            "RoleHarvester2" -> RoleHarvester2()
            "RoleUpgrader" -> RoleUpgrader()
            "RoleCarrier" -> RoleCarrier()
            "RoleBuilder" -> RoleBuilder()
            "RoleRepairer" -> RoleRepairer()
            "RoleFiller" -> RoleFiller()
            else -> null
        }
    } catch (e: Exception) {
        println("${roleClassName}类加载失败")
        e.printStackTrace()
    }
    return null
}

fun bruceChecker(checker: String, creep: Creep): Boolean {
    return when (checker) {
        Checkers.STORED_ENERGY_NONE.name -> {
            (creep.store.getUsedCapacity(RESOURCE_ENERGY) == 0)
        }

        else -> {
            true
        }
    }
}