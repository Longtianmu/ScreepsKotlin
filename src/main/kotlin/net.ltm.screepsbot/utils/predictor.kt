package net.ltm.screepsbot.utils

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.workRange
import screeps.api.*
import screeps.api.structures.Structure
import kotlin.math.min

val workPower = mapOf(
    Pair(Step.HARVEST.name, 2),
    Pair(Step.TRANSFER.name, 1),
    Pair(Step.WITHDRAW.name, 1),
    Pair(Step.BUILD.name, 5),
    Pair(Step.PICKUP.name, 1),
    Pair(Step.UPGRADE_CONTROLLER.name, 1),
    Pair(Step.REPAIR.name, 1)
)

// 预测下一tick是否能进行这个work
fun predict(creep: Creep, workType: Step, target: HasPosition): Boolean {
    if (!creep.pos.inRangeTo(target.pos, workRange[workType.name]!!.toInt() + 1)) return false
    when (workType) {
        in listOf(Step.BUILD, Step.UPGRADE_CONTROLLER, Step.REPAIR) -> {
            if (creep.store.getUsedCapacity(RESOURCE_ENERGY)!! -
                workPower[workType.name]!! * creep.getActiveBodyparts(WORK) < 0
            ) {
                return false
            }
            return when (workType) {
                Step.BUILD -> (target.unsafeCast<ConstructionSite>().progress + min(
                    workPower[workType.name]!! * creep.getActiveBodyparts(WORK),
                    creep.store.getUsedCapacity(RESOURCE_ENERGY)!!
                ) >= target.unsafeCast<ConstructionSite>().progressTotal)

                Step.REPAIR -> (target.unsafeCast<Structure>().hits + 100 * min(
                    workPower[workType.name]!! * creep.getActiveBodyparts(WORK),
                    creep.store.getUsedCapacity(RESOURCE_ENERGY)!!
                ) >= target.unsafeCast<Structure>().hitsMax)

                else -> true
            }
        }

        Step.HARVEST -> {
            return if (creep.getActiveBodyparts(CARRY) == 0) {
                true
            } else {
                (if (isSourceObject(target.unsafeCast<Identifiable>())) {
                    workPower[workType.name]!! * creep.getActiveBodyparts(WORK)
                } else {
                    creep.getActiveBodyparts(WORK)
                } >= creep.store.getFreeCapacity())
            }
        }

        Step.TRANSFER -> {
            creep.store.getUsedCapacity() +
                    target.unsafeCast<StoreOwner>().store.getFreeCapacity() >= target.unsafeCast<StoreOwner>().store.getCapacity(
                RESOURCE_ENERGY
            )

        }

        else -> return true
    }
    // 暂时没想到别的，摆烂了
}