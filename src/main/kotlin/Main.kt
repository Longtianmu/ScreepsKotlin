import net.ltm.screepsbot.mainLogic.gameLoop
import net.ltm.screepsbot.memory.scriptTicks
import screeps.api.Memory

@Suppress("unused")
fun loop() {
    Memory.scriptTicks++
    gameLoop()
}
