package com.reminder.hydration

sealed class Result {
    class Ok(val warning: String = "") : Result()
    class Error(val error: String) : Result()
}

operator fun Result.plus(other: Result): Result {
    return if (this > other)
        this
    else
        other
/*    if (this > other)
        return this

    return other

 */
}

operator fun Result.compareTo(other: Result): Int {
    if ((this is Result.Error) and (other is Result.Ok))
        return 1
    if ((other is Result.Error) and (this is Result.Ok))
        return -1
    if ((this is Result.Ok) and (other is Result.Ok))
        if (((this as Result.Ok).warning != "") and ((other as Result.Ok).warning == ""))
            return 1
    if (((this as Result.Ok).warning == "") and ((other as Result.Ok).warning != ""))
        return -1
    return 0
}
