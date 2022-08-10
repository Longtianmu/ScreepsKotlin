package net.ltm.screepsbot.constant

import net.ltm.screepsbot.profiles.childProfiles.RoleCarrier
import net.ltm.screepsbot.profiles.childProfiles.RoleHarvester1
import net.ltm.screepsbot.profiles.childProfiles.RoleHarvester2
import net.ltm.screepsbot.profiles.childProfiles.RoleUpgrader
import screeps.api.ResourceConstant

val resourceMap = mutableMapOf<String, ResourceConstant>()

val workRange = mapOf(
    Pair(Step.HARVEST.name, 1),
    Pair(Step.TRANSFER.name, 1),
    Pair(Step.WITHDRAW.name, 1),
    Pair(Step.BUILD.name, 3),
    Pair(Step.PICKUP.name, 1),
    Pair(Step.UPGRADE_CONTROLLER.name, 3),
    Pair(Step.REPAIR.name, 3)
)

val manualReflect = mapOf(
    Pair("RoleHarvester1", RoleHarvester1()),
    Pair("RoleHarvester2", RoleHarvester2()),
    Pair("RoleUpgrader", RoleUpgrader()),
    Pair("RoleCarrier", RoleCarrier())
)