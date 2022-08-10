package net.ltm.screepsbot.profiles

import net.ltm.screepsbot.memory.roleClass
import screeps.api.Creep

//data class TaskMem(val taskList: MutableList<String>, val options: MutableMap<String, MutableMap<String, String>>)
//
//fun profileLoader(id: Int): TaskMem {
//    val taskList = mutableListOf<String>()
//    return TaskMem(mutableListOf(), mutableMapOf())
//}

fun profileLoader() {

}

fun Creep.assign(role: Profile) {
    memory.roleClass = role::class.simpleName.toString()
}