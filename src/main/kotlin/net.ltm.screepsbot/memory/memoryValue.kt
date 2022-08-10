package net.ltm.screepsbot.memory

import net.ltm.screepsbot.constant.Role
import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.profiles.Profile
import net.ltm.screepsbot.profiles.childProfiles.RoleHarvester1
import net.ltm.screepsbot.profiles.childProfiles.RoleHarvester2
import screeps.api.CreepMemory
import screeps.api.MutableRecord
import screeps.api.RoomMemory
import screeps.utils.memory.memory
import screeps.utils.mutableRecordOf

//RoleSystem
var CreepMemory.from: String by memory { "null" }
var CreepMemory.to: String by memory { "null" }
var CreepMemory.building: Boolean by memory { false }
var CreepMemory.pause: Int by memory { 0 }
var CreepMemory.movePos: String by memory { "WTF" }//仅适用于Harvest等需要占坑位的
var CreepMemory.role by memory(Role.UNASSIGNED)

//StepSystem
var CreepMemory.init: Boolean by memory { false }
var CreepMemory.roleClass: String by memory { "null" }
var CreepMemory.taskList: Array<String> by memory { arrayOf() }
var CreepMemory.option: MutableRecord<String, MutableRecord<String, String>> by memory { mutableRecordOf() }
var CreepMemory.taskRetry: Int by memory { 0 }

//RoomSpawnSystem
val RoomMemory.maxCountMap: MutableRecord<String, Int> by memory {
    mutableRecordOf(
        Pair("RoleHarvester1", 3),
        Pair("RoleHarvester2", 3),
        Pair("RoleUpgrader", 3),
        Pair("RoleCarrier", 4),
        Pair("RoleBuilder", 3)
    )
}


