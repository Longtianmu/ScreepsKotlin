import net.ltm.screepsbot.mainLogic.exportStats
import net.ltm.screepsbot.mainLogic.gameLoop

@OptIn(ExperimentalJsExport::class)
@JsName("loop")
@JsExport
fun loop() {
    gameLoop()
    exportStats()
}
