package com.reminder.hydration

sealed class Result {
    class Ok(val warning: String = "") : Result()
    class Error(val error: String) : Result()
}

operator fun Result.plus(other: Result): Result {
    if (this is Result.Error)
        return this
    if (other is Result.Error)
        return other

    return this
}

operator fun Result.compareTo(other :Result) : Int {

}