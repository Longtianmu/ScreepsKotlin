package net.ltm.screepsbot.memory

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

//FindKSource
var RoomMemory.sourceList: Array<String> by memory { arrayOf() }

//RoomSpawnValue
val maxCountMap: MutableRecord<String, Int> = mutableRecordOf(
    Pair("RoleHarvester1", 3),
    Pair("RoleHarvester2", 3),
    Pair("RoleUpgrader", 4),
    Pair("RoleCarrier", 5),
    Pair("RoleBuilder", 3),
    Pair("RoleRepairer", 1),
    Pair("RoleFiller", 1)
)

val maxWorkCountMap: MutableRecord<String, Int> = mutableRecordOf(
    Pair("RoleHarvester1", 12),
    Pair("RoleHarvester2", 12),
    Pair("RoleUpgrader", 4),
    Pair("RoleCarrier", 5),
    Pair("RoleBuilder", 3),
    Pair("RoleRepairer", 1),
    Pair("RoleFiller", 1)
)


