package net.ltm.screepsbot.memory

import screeps.api.BodyPartConstant
import screeps.api.CreepMemory
import screeps.api.MutableRecord
import screeps.api.RoomMemory
import screeps.utils.memory.memory
import screeps.utils.mutableRecordOf

//StepSystem
var CreepMemory.init: Boolean by memory { false }
var CreepMemory.roleClass: String by memory { "" }
var CreepMemory.taskList: Array<String> by memory { arrayOf() }
var CreepMemory.option: MutableRecord<String, MutableRecord<String, String>> by memory { mutableRecordOf() }

//RoomSpawnSystem
var RoomMemory.maxCountMap: MutableRecord<String, Int> by memory { mutableRecordOf() }
var RoomMemory.maxWorkCountMap: MutableRecord<String, Int> by memory { mutableRecordOf() }
var RoomMemory.roomPriority: Array<String> by memory { arrayOf() }
var RoomMemory.spawnQueue: Array<Pair<String, Array<BodyPartConstant>>> by memory { arrayOf() }

//FindKSource
var RoomMemory.sourceList: Array<String> by memory { arrayOf() }

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

