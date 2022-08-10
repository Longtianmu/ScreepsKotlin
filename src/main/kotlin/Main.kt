import net.ltm.screepsbot.constant.resourceMap
import net.ltm.screepsbot.mainLogic.gameLoop
import screeps.api.RESOURCES_ALL
import screeps.api.value

var buildResourceMap = false

@OptIn(ExperimentalJsExport::class)
@JsName("loop")
@JsExport
fun loop() {
    if (!buildResourceMap) {
        for (i in RESOURCES_ALL) {
            resourceMap[i.value] = i
        }
        buildResourceMap = true
    }
    gameLoop()
}
