package net.ltm.screepsbot.mainLogic.spawnLoop

import net.ltm.screepsbot.mainLogic.roleDetector
import net.ltm.screepsbot.memory.*
import screeps.api.*
import screeps.api.structures.StructureSpawn
import screeps.utils.mutableRecordOf
import screeps.utils.unsafe.jsObject

fun spawnCreeps(creeps: List<Creep>, spawn: StructureSpawn) {
    val baseBody = arrayOf<BodyPartConstant>(WORK, CARRY, MOVE)
    val baseNoWorkBody = arrayOf<BodyPartConstant>(CARRY, MOVE)
    var body: Array<BodyPartConstant>
    val allEnergy = spawn.room.energyAvailable
    if (allEnergy < baseBody.sumOf { BODYPART_COST[it]!! }) {
        return
    }
    val maxCreepCount = spawn.room.memory.maxCountMap
    val roleClass: String = roleDetector(creeps, maxCreepCount, spawn) ?: return
    if (roleClass in listOf("RoleFiller", "RoleCarrier")) {
        body = baseNoWorkBody
        val baseBodyCost = baseNoWorkBody.sumOf { BODYPART_COST[it]!! }
        if (allEnergy < baseBodyCost) return
        val rate = allEnergy / 2 / baseBodyCost
        for (i in 1 until rate) {
            body = body.plus(baseNoWorkBody)
        }
    } else {
        body = baseBody
        val baseBodyCost = baseBody.sumOf { BODYPART_COST[it]!! }
        var rate = allEnergy / 2 / baseBodyCost
        if (roleClass.contains("RoleHarvester") && rate > 3) {
            rate = 3
        }
        if (allEnergy < baseBodyCost * rate) return
        for (i in 1 until rate) {
            body = body.plus(baseBody)
        }
    }

    val newName = "${roleClass}_${Game.time}"
    val code = spawn.spawnCreep(body, newName, options {
        memory = jsObject<CreepMemory> {
            this.roleClass = roleClass
            this.init = false
            this.option = mutableRecordOf()
            this.taskList = arrayOf()
        }
    })

    when (code) {
        OK -> console.log("使用部件${body}生成了新Creep:${newName}")
        ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { } // do nothing
        else -> console.log("无法解析错误代码$code")
    }
}