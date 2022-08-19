package net.ltm.screepsbot.constant

import net.ltm.screepsbot.constant.returnCode.Checkers
import net.ltm.screepsbot.profiles.childProfiles.*
import screeps.api.Creep
import screeps.api.MutableRecord
import screeps.api.RESOURCE_ENERGY
import screeps.utils.mutableRecordOf

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
    Pair("RoleClaimer", RoleClaimer()),
    Pair("RoleDestroyer", RoleDestroyer())
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

//RoomSpawnValue
val maxCountMap: MutableRecord<String, Int> = mutableRecordOf(
    Pair("RoleHarvester1", 3),
    Pair("RoleHarvester2", 3),
    Pair("RoleUpgrader", 3),
    Pair("RoleCarrier", 5),
    Pair("RoleBuilder", 3),
    Pair("RoleRepairer", 1),
    Pair("RoleFiller", 1)
)
val maxWorkCountMap: MutableRecord<String, Int> = mutableRecordOf(
    Pair("RoleHarvester1", 8),
    Pair("RoleHarvester2", 8),
    Pair("RoleUpgrader", 4),
    Pair("RoleCarrier", 8),
    Pair("RoleBuilder", 4),
    Pair("RoleRepairer", 2),
    Pair("RoleFiller", 4)
)
val roomPriority: Array<String> = arrayOf(
    "RoleHarvester1",
    "RoleHarvester2",
    "RoleCarrier",
    "RoleUpgrader",
    "RoleFiller",
    "RoleRepairer",
    "RoleBuilder",
)