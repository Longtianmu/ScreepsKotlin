package net.ltm.screepsbot.constant.returnCode

enum class GeneratorReturnCode {
    OK,//创建成功
    FAILED,//不可预料的创建失误
    NO_TARGET//无可用目标，可直接SKIP
}