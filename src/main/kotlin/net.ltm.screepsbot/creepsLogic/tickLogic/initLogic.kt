package net.ltm.screepsbot.creepsLogic.tickLogic

import net.ltm.screepsbot.constant.reflection
import net.ltm.screepsbot.constant.returnCode.GeneratorReturnCode
import net.ltm.screepsbot.constant.returnCode.InitialReturnCode
import net.ltm.screepsbot.memory.init
import net.ltm.screepsbot.memory.option
import net.ltm.screepsbot.memory.roleClass
import net.ltm.screepsbot.memory.taskList
import net.ltm.screepsbot.profiles.Profile
import screeps.api.Creep
import screeps.utils.mutableRecordOf

fun Creep.init(): InitialReturnCode {
    val roleClass: Profile = reflection[memory.roleClass] ?: return InitialReturnCode.FAILED
    val returnCode: GeneratorReturnCode
    var resultString = memory.roleClass
    if (!memory.init) {
        memory.option = mutableRecordOf()
        memory.taskList = roleClass.preTask.toTypedArray() // taskList
        memory.init = true
        returnCode = roleClass.initGenerator(this) // option
        resultString += ("的Initial阶段")
    } else {
        memory.taskList = roleClass.taskLoop.toTypedArray()
        returnCode = roleClass.loopGenerator(this) // option
        resultString += ("的Loop阶段")
    }
    return when (returnCode) {
        GeneratorReturnCode.OK -> {
            InitialReturnCode.OK
        }

        GeneratorReturnCode.FAILED -> {
            println(resultString + "发生了错误，请查看是否存在Null情况")
            InitialReturnCode.FAILED
        }

        GeneratorReturnCode.NO_TARGET -> {
            InitialReturnCode.SKIP
        }
    }
}