package net.ltm.screepsbot.memory

import net.ltm.screepsbot.constant.Role
import screeps.api.CreepMemory
import screeps.api.Memory
import screeps.api.RoomMemory
import screeps.utils.memory.memory

//perCreepsMemory
var CreepMemory.from: String by memory { "" }
var CreepMemory.to: String by memory { "" }
var CreepMemory.building: Boolean by memory { false }
var CreepMemory.pause: Int by memory { 0 }
var CreepMemory.movePos: String by memory { "WTF" }//仅适用于Harvest等需要占坑位的
var CreepMemory.role by memory(Role.UNASSIGNED)

var Memory.scriptTicks by memory { 0 }

var RoomMemory.numberOfCreeps: Int by memory { 0 }