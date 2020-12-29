package com.reminder.hydration

import android.app.Notification
import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EntryViewModel(private val sharedPrefManager: SharedPrefManager) : ViewModel() {

    private val wakeUpTimeData: MutableLiveData<Int> = MutableLiveData()
    private val sleepTimeData: MutableLiveData<Int> = MutableLiveData()
    private val drunkenWaterAmountData: MutableLiveData<Int> = MutableLiveData()
    private val soundTypeData: MutableLiveData<Int> = MutableLiveData()
    private val notifTypeData: MutableLiveData<Int> = MutableLiveData()
    private val warning: MutableLiveData<String> = MutableLiveData<String>()
    private val error: MutableLiveData<String> = MutableLiveData<String>()

    fun wakeUpTime(): LiveData<Int> = wakeUpTimeData
    fun sleepTime(): LiveData<Int> = sleepTimeData
    fun drunkenWaterAmount(): LiveData<Int> = drunkenWaterAmountData
    fun notifType(): LiveData<Int> = notifTypeData
    fun soundType(): LiveData<Int> = soundTypeData


    fun loadData() {
        wakeUpTimeData.value = sharedPrefManager.load(wakeUpTimeKey, 0)
        sleepTimeData.value = sharedPrefManager.load(sleepTimeKey, 0)
        drunkenWaterAmountData.value = sharedPrefManager.load(waterNumKey, 0)
        notifTypeData.value = sharedPrefManager.load(notifTypeKey, NotifType.NOTIFICATION.value)
        soundTypeData.value = sharedPrefManager.load(soundTypeKey, SoundType.SOUND.value)
    }


    fun warning(): LiveData<String> = warning
    fun error(): LiveData<String> = error

    fun saveData(
        wakeUpTime: Int,
        sleepTime: Int,
        waterNum: Int,
        notifType: Int,
        soundType: Int
    ) {
        val validated = validateData(wakeUpTime, sleepTime, waterNum)
        if (validated is Result.Error) {
            error.value = validated.error
            return
        }
        if (validated is Result.Ok && validated.warning.isNotEmpty()) {
            warning.value = validated.warning
        }

        sharedPrefManager.save(sleepTimeKey, sleepTime)
        sharedPrefManager.save(wakeUpTimeKey, wakeUpTime)
        sharedPrefManager.save(waterNumKey, waterNum)
        sharedPrefManager.save(notifTypeKey, notifType)
        sharedPrefManager.save(soundTypeKey, soundType)
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

class EntryViewModelFactory(private val sharedPrefManager: SharedPrefManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EntryViewModel(sharedPrefManager) as T
    }

}