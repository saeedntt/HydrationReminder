package com.reminder.hydration

sealed class Result {
    class Ok(val warning: String = "") : Result()
    class Error(val message: String) : Result()
}

operator fun Result.plus(other: Result): Result {
    if (this is Error)
        return this
    if (other is Error)
        return other

    return this
}