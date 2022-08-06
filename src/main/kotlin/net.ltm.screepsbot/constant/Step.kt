package net.ltm.screepsbot.constant

import net.ltm.screepsbot.utils.findGeneralStoreOwner
import screeps.api.Room

enum class Step {
    MOVE {
        override fun getTarget(room: Room): String {
            TODO("Not yet implemented")
        }
    },
    HARVEST {
        override fun getTarget(room: Room): String {
            TODO("Not yet implemented")
        }
    },
    TRANSFER {
        override fun getTarget(room: Room): String {
            return room.findGeneralStoreOwner().firstOrNull()?.id!!
        }
    },
    BUILD {
        override fun getTarget(room: Room): String {
            TODO("Not yet implemented")
        }
    },
    REPAIR {
        override fun getTarget(room: Room): String {
            TODO("Not yet implemented")
        }
    },
    WITHDRAW {
        override fun getTarget(room: Room): String {
            TODO("Not yet implemented")
        }
    },
    PICKUP {
        override fun getTarget(room: Room): String {
            TODO("Not yet implemented")
        }
    },
    UPGRADE_CONTROLLER {
        override fun getTarget(room: Room): String {
            TODO("Not yet implemented")
        }
    };

    abstract fun getTarget(room: Room): String
}
