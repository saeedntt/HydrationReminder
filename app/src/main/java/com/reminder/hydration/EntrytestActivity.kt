package com.reminder.hydration

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EntrytestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrytest)

        val sharedPref = getSharedPreferences("EntryPageAnswers", Context.MODE_PRIVATE)
        arrayOf<TextView>(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5)
        ).apply {
            for (key in 0..this.size - 1) {
                this[key].text = sharedPref.getString(key.toString(), "")
            }
        }
    }
}
