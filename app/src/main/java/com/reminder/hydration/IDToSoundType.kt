package com.reminder.hydration

object IDToSoundType {
    fun of (viewID : Int) : SoundType {
        return when (viewID) {
            R.id.notificationSoundYes -> SoundType.SOUND
            R.id.notificationSoundNo -> SoundType.MUTE
            R.id.notificationSoundVibrate -> SoundType.VIBRATE
            else -> SoundType.SOUND
        }
    }
}