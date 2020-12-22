package com.reminder.hydration

enum class SoundType(val value: Int) {
    SOUND(0),
    MUTE(1),
    VIBRATE(2);
    /*SOUND(R.id.notificationSoundYes),
    MUTE(R.id.notificationSoundNo),
    VIBRATE(R.id.notificationSoundVibrate);

     */

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