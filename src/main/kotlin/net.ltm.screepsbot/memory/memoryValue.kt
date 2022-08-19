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

