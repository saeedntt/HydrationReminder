package com.reminder.hydration

object IDToNotiftype {
    fun of(viewID: Int): NotifType {
        return when (viewID) {
            R.id.waterReminderNotificationMethod -> NotifType.NOTIFICATION
            R.id.waterReminderFullscreenMethod -> NotifType.ALARM
            else -> NotifType.NOTIFICATION
        }
    }
}