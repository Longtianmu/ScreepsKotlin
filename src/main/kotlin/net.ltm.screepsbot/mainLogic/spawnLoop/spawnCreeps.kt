package net.ltm.screepsbot.mainLogic.spawnLoop

import net.ltm.screepsbot.memory.init
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.roleClass
import net.ltm.screepsbot.memory.taskList
import screeps.api.*
import screeps.api.structures.StructureSpawn
import screeps.utils.mutableRecordOf
import screeps.utils.unsafe.jsObject

fun spawnCreeps(spawn: StructureSpawn, roomCreeps: List<Creep>) {
    val allEnergy = spawn.room.energyAvailable
    if (allEnergy < 200) {
        return
    }
    val roles = roleDetector(roomCreeps, spawn) ?: return
    val roleClass = roles.first
    val roleBody = roles.second

    val name = "${roleClass}_${Game.shard.name}_${spawn.room.name}_${Game.time}"

    val code = spawn.spawnCreep(roleBody, name, options {
        memory = jsObject<CreepMemory> {
            this.roleClass = roleClass
            this.init = false
            this.option = mutableRecordOf()
            this.taskList = arrayOf()
        }
    })

    when (code) {
        OK -> console.log("使用部件${roleBody}生成了新Creep:${name}")
        ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { }
        else -> console.log("无法解析错误代码$code")
    }
}