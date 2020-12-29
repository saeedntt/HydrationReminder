package com.reminder.hydration


enum class NotifType(val value: Int) {
    NOTIFICATION(0),
    ALARM(1);

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