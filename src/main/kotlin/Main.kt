import net.ltm.screepsbot.mainLogic.exportStats
import net.ltm.screepsbot.mainLogic.gameLoop

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() {
    gameLoop()
    exportStats()
}
