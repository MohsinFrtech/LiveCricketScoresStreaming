package com.traumsportzone.live.cricket.tv.scores.streaming.emulator

sealed class DeviceState {

    class Emulator(
        val source: VerdictSource
    ) : DeviceState()

    object NotEmulator : DeviceState()
}