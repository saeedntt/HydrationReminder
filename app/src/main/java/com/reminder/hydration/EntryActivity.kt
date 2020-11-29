package com.reminder.hydration

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class EntryActivity : AppCompatActivity() {

    private val sharedPrefManager by lazy {
        SharedPrefManager.getInstance(this)
    }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val waterReminderNotifGroup = findViewById<RadioGroup>(R.id.waterReminderNotifGroup)
        val notificationSoundGroup = findViewById<RadioGroup>(R.id.notificationSoundGroup)
        val entryPageSubmitButton = findViewById<Button>(R.id.entryPageSubmitButton)
        val wakeUpTimeError = findViewById<TextView>(R.id.wakeUpTimeError)
        val sleepTimeError = findViewById<TextView>(R.id.sleepTimeError)

        val viewModel: EntryViewModel by viewModels {
            EntryViewModelFactory(sharedPrefManager)
        }

        viewModel.error().observe(this) { error ->
            //TODO: Show the error
        }

        //TODO: Observe the warning and show the message

        entryPageSubmitButton.setOnClickListener {
            val wakeUpTime = findViewById<EditText>(R.id.wakeUpTimeH).text.toString().toInt()
            val sleepTime = findViewById<EditText>(R.id.sleepTimeH).text.toString().toInt()
            val waterNum = findViewById<EditText>(R.id.drunkenWaterAmount).text.toString().toInt()
            val notifType = NotifType.of(waterReminderNotifGroup.checkedRadioButtonId)
            val soundType = SoundType.of(notificationSoundGroup.checkedRadioButtonId)
            viewModel.saveData(wakeUpTime, sleepTime, waterNum, notifType, soundType)
        }
    }
}
