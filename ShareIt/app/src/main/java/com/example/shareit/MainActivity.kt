package com.example.shareit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonShare.setOnClickListener{ handleShare() }
    }

    fun handleShare(){
        var message = txtMessage.text.toString()
        val shareIntent = Intent(Intent.ACTION_SEND)
        with(shareIntent) {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "ShareIt")
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startActivity(shareIntent)
    }
}
