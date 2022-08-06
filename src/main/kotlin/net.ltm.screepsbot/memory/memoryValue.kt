package net.ltm.screepsbot.memory

import net.ltm.screepsbot.constant.Role
import screeps.api.CreepMemory
import screeps.utils.memory.memory

var CreepMemory.from: String by memory { "null" }
var CreepMemory.to: String by memory { "null" }
var CreepMemory.building: Boolean by memory { false }
var CreepMemory.pause: Int by memory { 0 }
var CreepMemory.movePos: String by memory { "WTF" }//仅适用于Harvest等需要占坑位的
var CreepMemory.role by memory(Role.UNASSIGNED)
var CreepMemory.baseProfile: Int by memory { 0 }
var CreepMemory.taskList: MutableList<String> by memory { mutableListOf() }
var CreepMemory.option: MutableMap<String, MutableMap<String, String>> by memory { mutableMapOf() }
var CreepMemory.taskRetry: Int by memory { 0 }
