package com.reminder.hydration

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        val entryPageSubmitButton = findViewById<Button>(R.id.entryPageSubmitButton)
        val wakeUpTimeError = findViewById<TextView>(R.id.wakeUpTimeError)
        val sleepTimeError = findViewById<TextView>(R.id.sleepTimeError)
        val entryActivityAnswers = arrayOf(
            findViewById<EditText>(R.id.wakeUpTimeH),
            findViewById<EditText>(R.id.wakeUpTimeM),
            findViewById<EditText>(R.id.sleepTimeH),
            findViewById<EditText>(R.id.sleepTimeM),
            findViewById<EditText>(R.id.drunkenWaterAmount),
            findViewById<RadioButton>(R.id.waterReminderNotificationMethod),
            findViewById<RadioButton>(R.id.waterReminderFullscreenMethod),
            findViewById<RadioButton>(R.id.notificationSoundYes),
            findViewById<RadioButton>(R.id.notificationSoundNo),
            findViewById<RadioButton>(R.id.notificationSoundVibrate)
        )
        val sharedPref = getSharedPreferences("EntryPageAnswers", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()

        entryPageSubmitButton.setOnClickListener {
            entryActivityAnswers.forEach {
                val key = it.id.toString()
                sharedPrefEditor.putString("$key", it.text.toString())
            }
        sharedPrefEditor.apply()
        }
    }
}