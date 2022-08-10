import net.ltm.screepsbot.mainLogic.gameLoop
import net.ltm.screepsbot.memory.resourceMap

import screeps.api.RESOURCES_ALL
import screeps.api.value

var buildResourceMap = true

@Suppress("unused")
fun loop() {
    if (buildResourceMap) {
        for (i in RESOURCES_ALL) {
            resourceMap[i.value] = i
        }
        buildResourceMap = false
    }
    gameLoop()
}
