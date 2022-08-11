package net.ltm.screepsbot.constant

import net.ltm.screepsbot.profiles.Profile
import net.ltm.screepsbot.profiles.childProfiles.*

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
}