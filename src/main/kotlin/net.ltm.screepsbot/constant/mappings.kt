package net.ltm.screepsbot.constant

import net.ltm.screepsbot.constant.returnCode.Checkers
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

val reflection = hashMapOf(
    Pair("RoleHarvester1", RoleHarvester1()),
    Pair("RoleHarvester2", RoleHarvester2()),
    Pair("RoleUpgrader", RoleUpgrader()),
    Pair("RoleCarrier", RoleCarrier()),
    Pair("RoleBuilder", RoleBuilder()),
    Pair("RoleRepairer", RoleRepairer()),
    Pair("RoleFiller", RoleFiller()),
    Pair("RoleClaimer", RoleClaimer())
)

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