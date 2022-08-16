import net.ltm.screepsbot.mainLogic.gameLoop
import net.ltm.screepsbot.utils.statsUtils.exportStats

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() {
    gameLoop()
    exportStats()
}
