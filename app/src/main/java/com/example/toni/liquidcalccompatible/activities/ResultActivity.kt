package com.example.toni.liquidcalccompatible.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.toni.liquidcalccompatible.R
import java.util.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)

        val intent = intent

        val shotMenge = intent.getStringExtra("shotMenge")
        val aromaMenge = intent.getStringExtra("aromaMenge")
        val textView = findViewById<TextView>(R.id.textView)
        textView.keyListener = null
        val text = String.format(Locale.GERMANY, textView.text.toString(), shotMenge, aromaMenge)

        textView.text = text
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        this.finish()
        return super.onSupportNavigateUp()
    }
}
