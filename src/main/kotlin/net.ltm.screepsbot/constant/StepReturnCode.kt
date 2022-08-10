package net.ltm.screepsbot.constant

enum class StepReturnCode {
    OK,
    ERR_NEED_RESET,
    ERR_NEED_MOVE,
    STATUS_IN_PROGRESS,
    SKIP_TICK,
}

enum class TickReturnCode {
    OK,
    REWORK
}