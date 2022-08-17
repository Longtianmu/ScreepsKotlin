package net.ltm.screepsbot.mainLogic.spawnLoop

import net.ltm.screepsbot.memory.*
import screeps.api.*
import screeps.api.structures.StructureSpawn
import screeps.utils.mutableRecordOf
import screeps.utils.unsafe.jsObject

fun spawnCreeps(spawn: StructureSpawn) {
    if (spawn.room.energyAvailable < 200 || spawn.room.memory.spawnQueue.isEmpty()) {
        return
    }
    //val roles = roleDetector(roomCreeps, spawn) ?: return
    val roles = spawn.room.memory.spawnQueue.first()
    val roleClass = roles.first
    val roleBody = roles.second

    if (roleClass.isEmpty() || roleBody.isEmpty()) {
        spawn.room.memory.spawnQueue = spawn.room.memory.spawnQueue.drop(1).toTypedArray()
        println("遇到空队列")
    }
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
        OK -> {
            spawn.room.memory.spawnQueue = spawn.room.memory.spawnQueue.drop(1).toTypedArray()
            println("使用部件${roleBody}生成了新Creep:${name}")
        }

        ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { }
        else -> console.log("无法解析错误代码$code")
    }
}