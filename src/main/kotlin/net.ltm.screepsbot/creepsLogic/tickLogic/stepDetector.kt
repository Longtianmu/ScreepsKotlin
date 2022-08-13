package net.ltm.screepsbot.creepsLogic.tickLogic

import net.ltm.screepsbot.constant.Step
import net.ltm.screepsbot.constant.returnCode.StepReturnCode
import net.ltm.screepsbot.creepsLogic.stepLogic.*
import screeps.api.Creep

fun stepDetector(creep: Creep, currentTask: String): StepReturnCode {
    return when (currentTask) {
        Step.MOVE.name -> {
            stepMove(creep)
        }

        Step.HARVEST.name -> {
            stepHarvest(creep)
        }

        Step.TRANSFER.name -> {
            stepTransfer(creep)
        }

        Step.BUILD.name -> {
            stepBuild(creep)
        }

        Step.UPGRADE_CONTROLLER.name -> {
            stepUpdateControllerLogic(creep)
        }

        Step.WITHDRAW.name -> {
            stepWithdraw(creep)
        }

        Step.PICKUP.name -> {
            stepPickup(creep)
        }

        Step.REPAIR.name -> {
            stepRepair(creep)
        }

        Step.RE_INIT.name -> {
            stepReInit(creep)
        }

        else -> StepReturnCode.OK // unknown Step
    }
}