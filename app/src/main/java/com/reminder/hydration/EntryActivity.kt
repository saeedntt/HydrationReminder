package com.reminder.hydration

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntryActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        val waterReminderNotifGroup = findViewById<RadioGroup>(R.id.waterReminderNotifGroup)
        val notificationSoundGroup = findViewById<RadioGroup>(R.id.notificationSoundGroup)
        val entryPageSubmitButton = findViewById<Button>(R.id.entryPageSubmitButton)
        val wakeUpTimeError = findViewById<TextView>(R.id.wakeUpTimeError)
        val sleepTimeError = findViewById<TextView>(R.id.sleepTimeError)
        val sharedPref = getSharedPreferences("EntryPageAnswers", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        entryPageSubmitButton.setOnClickListener {
            arrayOf(
                findViewById<EditText>(R.id.wakeUpTimeH),
                findViewById<EditText>(R.id.sleepTimeH),
                findViewById<EditText>(R.id.drunkenWaterAmount),
                findViewById<RadioButton>(waterReminderNotifGroup.checkedRadioButtonId),
                findViewById<RadioButton>(notificationSoundGroup.checkedRadioButtonId)
            ).apply {
                if ((this[0].text.toString().toInt() > 24) or (this[0].text.toString().toInt() < 0)) {
                    wakeUpTimeError.text = "Please enter a valid time."
                } else if ((this[1].text.toString().toInt() > 24) or (this[1].text.toString().toInt() < 0)) {
                    sleepTimeError.text = "Please enter a valid time."
                } else {
                    for (key in 0..this.size - 1) {
                        sharedPrefEditor.putString(key.toString(), this[key].text.toString())
                    }
                    sharedPrefEditor.apply()
                    startActivity(Intent(this@EntryActivity, EntrytestActivity::class.java))
                }
            }

        }
    }
}
