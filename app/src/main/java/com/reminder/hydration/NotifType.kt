package com.reminder.hydration

enum class NotifType(val value: Int) {
    NOTIFICATION(R.id.waterReminderNotificationMethod),
    ALARM(R.id.waterReminderFullscreenMethod);

    companion object {
        fun of(value: Int): NotifType {
            values().forEach {
                if (it.value == value) {
                    return it
                }
            }
            return NOTIFICATION
        }
    }

}