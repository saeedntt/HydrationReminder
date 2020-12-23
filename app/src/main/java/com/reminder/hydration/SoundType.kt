package com.reminder.hydration

enum class SoundType(val value: Int) {
    SOUND(0),
    MUTE(1),
    VIBRATE(2);

    companion object {
        fun of(value: Int): SoundType {
            values().forEach {
                if (it.value == value) {
                    return it
                }
            }
            return SOUND
        }
    }
}