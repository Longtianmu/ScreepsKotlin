package net.ltm.screepsbot.utils.roomUtils

import screeps.api.FIND_SOURCES
import screeps.api.Room
import screeps.api.Source

fun findKSource(room: Room, k: Int): Source? {
    val sources = room.find(FIND_SOURCES)
    return sources[k].unsafeCast<Source?>()
}