package net.ltm.screepsbot.mainLogic.spawnLoop

import net.ltm.screepsbot.constant.reflection
import net.ltm.screepsbot.memory.*
import screeps.api.*

fun spawnQueuePusher(creeps: List<Creep>, room: Room) {
    val allEnergy = room.energyAvailable
    val maxCreepCount = room.memory.maxCountMap
    val maxWorkCount = room.memory.maxWorkCountMap
    val roomPriority = room.memory.roomPriority

    val nowCreepCount = mutableMapOf<String, Int>()
    val nowWorkCount = mutableMapOf<String, Int>()

    if (allEnergy < 200) {
        return
    }
    for (i in creeps) {
        if (i.ticksToLive < i.body.count() * 3) {
            continue
        }
        nowCreepCount[i.memory.roleClass] = (nowCreepCount[i.memory.roleClass] ?: 0) + 1
        nowWorkCount[i.memory.roleClass] = (nowWorkCount[i.memory.roleClass] ?: 0) + i.getActiveBodyparts(
            reflection[i.memory.roleClass]?.tag.unsafeCast<ActiveBodyPartConstant>()
        )
    }
    for (i in room.memory.spawnQueue) {
        nowCreepCount[i.first] = (nowCreepCount[i.first] ?: 0) + 1
        nowWorkCount[i.first] = (nowWorkCount[i.first] ?: 0) + i.second.count { it == reflection[i.first]?.tag }
    }

    for (i in roomPriority) {
        if ((nowCreepCount[i] >= maxCreepCount[i]) || (nowWorkCount[i] >= maxWorkCount[i])) {
            continue
        }
        val tag = reflection[i]?.tag!!
        val baseBody = arrayOf(tag, MOVE)
        var body = baseBody
        var rate = allEnergy / 2 / baseBody.sumOf { BODYPART_COST[it]!! }
        if (rate > maxWorkCount[i]) {
            rate = maxWorkCount[i]!!
        }
        for (j in 1..rate) {
            body = body.plus(baseBody)
        }
        if (tag in listOf(WORK)) {
            body = body.plus(CARRY)
            for (k in 2..rate / 3) {
                body = body.plus(CARRY)
            }
        }
        room.memory.spawnQueue = room.memory.spawnQueue.plus(Pair(i, body))
        return
    }
}