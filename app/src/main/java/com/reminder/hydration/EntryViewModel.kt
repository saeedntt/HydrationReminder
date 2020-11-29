package com.reminder.hydration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EntryViewModel(private val sharedPrefManager: SharedPrefManager) : ViewModel() {
    //TODO: Get the data from sharedpref and notify the view of the new data
    private val warning: MutableLiveData<String> = MutableLiveData<String>()
    private val error: MutableLiveData<String> = MutableLiveData<String>()

    fun warning(): LiveData<String> = warning
    fun error(): LiveData<String> = error

    fun saveData(wakeUpTime: Int, sleepTime:Int, waterNum: Int, notifType: NotifType, soundType: SoundType) {
        val validated = validateData(wakeUpTime, sleepTime, waterNum)
        if (validated is Result.Error) {
            error.value = validated.message
            return
        }
        if (validated is Result.Ok && validated.warning.isNotEmpty()) {
            warning.value = validated.warning
        }

        sharedPrefManager.save(wakeUpTimeKey, wakeUpTime)
        sharedPrefManager.save(sleepTimeKey, sleepTime)
        sharedPrefManager.save(waterNumKey, waterNum)
        sharedPrefManager.save(notifTypeKey, notifType.value)
        sharedPrefManager.save(soundTypeKey, soundType.value)
    }

    private fun validateData(wakeUpTime: Int, sleepTime: Int, waterNum: Int): Result {
        return validateWaterNum(waterNum) + validateTime(wakeUpTime, sleepTime)
    }

    private fun validateWaterNum(waterNum: Int): Result {
        if (waterNum > 10) {
            return Result.Ok("Are you sure you want to drink that much?")
        }
        return Result.Ok()
    }

    private fun validateTime(vararg time: Int): Result {
        time.forEach {
            if (it > 24 || it < 0) {
                return Result.Error("Time must be between 0 and 24")
            }
        }
        return Result.Ok()
    }

    companion object {
        const val wakeUpTimeKey = "wakeTime"
        const val sleepTimeKey = "sleepTime"
        const val waterNumKey = "waterNum"
        const val notifTypeKey = "notifType"
        const val soundTypeKey = "soundType"
    }
}

class EntryViewModelFactory(private val sharedPrefManager: SharedPrefManager): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EntryViewModel(sharedPrefManager) as T
    }

}