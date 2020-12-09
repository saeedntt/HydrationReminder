package com.reminder.hydration

import android.annotation.SuppressLint
import android.graphics.Color
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
        val entryPageMessage = findViewById<TextView>(R.id.entryPageMessage)

        val viewModel: EntryViewModel by viewModels {
            EntryViewModelFactory(sharedPrefManager)
        }

        viewModel.error().observe(this) { error ->
            setMessageAndColor(entryPageMessage, error, Color.RED)
        }

        viewModel.warning().observe(this) { warning ->
            setMessageAndColor(entryPageMessage, warning, Color.YELLOW)
        }

        entryPageSubmitButton.setOnClickListener {
            val wakeUpTime = findViewById<EditText>(R.id.wakeUpTimeH).text.toString().toInt()
            val sleepTime = findViewById<EditText>(R.id.sleepTimeH).text.toString().toInt()
            val waterNum = findViewById<EditText>(R.id.drunkenWaterAmount).text.toString().toInt()
            val notifType = NotifType.of(waterReminderNotifGroup.checkedRadioButtonId)
            val soundType = SoundType.of(notificationSoundGroup.checkedRadioButtonId)
            viewModel.saveData(wakeUpTime, sleepTime, waterNum, notifType, soundType)
        }
    }

    private fun setMessageAndColor(entryPageMessage: TextView, error: String?, color: Int) {
        entryPageMessage.text = error
        entryPageMessage.setTextColor(color)
    }
}
