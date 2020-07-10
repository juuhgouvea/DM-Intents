package com.example.receiveit

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleReceive()
    }

    private fun handleReceive(){
        if (intent.action == Intent.ACTION_SEND && intent.type != null) {
            if ("text/plain" == intent.type) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (sharedText != null)
                    receive_message.text = sharedText
            }
            if ("image/*" == intent.type) {
                val stream = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM);
                if (stream != null)
                    sharedImage.setImageURI(stream)
            }
        }
    }
}
