package com.traumsportzone.live.cricket.tv.scores.streaming.emulator.sensor

import android.content.Context
import com.traumsportzone.live.cricket.tv.scores.streaming.emulator.DeviceState
import com.traumsportzone.live.cricket.tv.scores.streaming.emulator.EmulatorDetector
import com.traumsportzone.live.cricket.tv.scores.streaming.emulator.VerdictSource

internal class SensorEmulatorDetector(
    context: Context,
    sensorType: Int,
    private val eventsCount: Int
) : EmulatorDetector() {

    private val sensorDataProcessor = SensorDataValidator()
    private val sensorEventProducer = SensorEventProducer(context, sensorType)

    override suspend fun getState(): DeviceState {
        val sensorEvents = sensorEventProducer.getSensorEvents(eventsCount)
            .onFailure { return DeviceState.NotEmulator }
            .getOrThrow()

        return if (sensorDataProcessor.isEmulator(sensorEvents)) {
            DeviceState.Emulator(VerdictSource.Sensors(sensorEvents))
        } else {
            DeviceState.NotEmulator
        }
    }
}