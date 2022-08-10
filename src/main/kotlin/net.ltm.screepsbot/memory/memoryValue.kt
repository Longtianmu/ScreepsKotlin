package net.ltm.screepsbot.memory

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.Profile
import net.ltm.screepsbot.profiles.childProfiles.RoleHarvester1
import net.ltm.screepsbot.profiles.childProfiles.RoleHarvester2
import screeps.api.CreepMemory
import screeps.api.ResourceConstant
import screeps.utils.memory.memory


var CreepMemory.from: String by memory { "null" }
var CreepMemory.to: String by memory { "null" }
var CreepMemory.building: Boolean by memory { false }
var CreepMemory.pause: Int by memory { 0 }
var CreepMemory.movePos: String by memory { "WTF" }//仅适用于Harvest等需要占坑位的
var CreepMemory.role by memory(Role.UNASSIGNED)
var CreepMemory.init: Boolean by memory { false }
var CreepMemory.roleClass: String by memory { "null" }
var CreepMemory.baseProfile: Int by memory { 0 }
var CreepMemory.taskList: MutableList<String> by memory { mutableListOf() }
var CreepMemory.option: MutableMap<String, MutableMap<String, String>> by memory { mutableMapOf() }
var CreepMemory.taskRetry: Int by memory { 0 }
var CreepMemory.test: dynamic by memory { 0 }

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
val manualReflect = mapOf<String, Profile>(
    Pair("RoleHarvester1", RoleHarvester1()),
    Pair("RoleHarvester2", RoleHarvester2())
)