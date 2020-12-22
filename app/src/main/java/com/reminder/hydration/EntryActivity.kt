package com.reminder.hydration

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
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

        val wakeUpTime = findViewById<EditText>(R.id.wakeUpTimeH)
        val sleepTime = findViewById<EditText>(R.id.sleepTimeH)
        val waterNum = findViewById<EditText>(R.id.drunkenWaterAmount)


        viewModel.loadData()
        viewModel.wakeUpTime().observe(this) {
            wakeUpTime.setText(it.toString())
        }
        viewModel.sleepTime().observe(this) {
            sleepTime.setText(it.toString())
        }
        viewModel.drunkenWaterAmount().observe(this) {
            waterNum.setText(it.toString())
        }
        viewModel.soundType().observe(this) {
            notificationSoundGroup.check(it)
        }
        viewModel.notifType().observe(this) {
            waterReminderNotifGroup.check(it)
        }

        viewModel.error().observe(this) { error ->
            setMessageAndColor(entryPageMessage, error, Color.RED)
        }

        viewModel.warning().observe(this) { warning ->
            setMessageAndColor(entryPageMessage, warning, Color.YELLOW)
        }

        entryPageSubmitButton.setOnClickListener {
            val wakeUpTimeValue = wakeUpTime.text.toString().toInt()
            val sleepTimeValue = sleepTime.text.toString().toInt()
            val waterNumValue = waterNum.text.toString().toInt()
            val notifTypeValue = NotifType.of(waterReminderNotifGroup.checkedRadioButtonId)
            val soundTypeValue = SoundType.of(notificationSoundGroup.checkedRadioButtonId)
            viewModel.saveData(wakeUpTimeValue, sleepTimeValue, waterNumValue, notifTypeValue, soundTypeValue)
        }
    }

    private fun setMessageAndColor(entryPageMessage: TextView, error: String?, color: Int) {
        entryPageMessage.text = error
        entryPageMessage.setTextColor(color)
    }
}
