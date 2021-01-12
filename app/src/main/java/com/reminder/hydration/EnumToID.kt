package com.reminder.hydration

object EnumToID {
    fun of(notifType: NotifType): Int {
        return when (notifType) {
            NotifType.NOTIFICATION -> R.id.waterReminderNotificationMethod
            NotifType.ALARM -> R.id.waterReminderFullscreenMethod
        }
    }

    fun of(soundType: SoundType): Int {
        return when (soundType) {
            SoundType.SOUND -> R.id.notificationSoundYes
            SoundType.MUTE -> R.id.notificationSoundNo
            SoundType.VIBRATE -> R.id.notificationSoundVibrate
        }
    }
}

