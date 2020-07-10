package com.example.shareit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var imageURI: Uri? = null;
    private val PICK_IMAGE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonShareText.setOnClickListener{ handleShareText() }
        buttonShareImage.setOnClickListener{ handleShareImage() }
        buttonChooseImage.setOnClickListener { handleChooseImage() }
    }

    private fun handleShareText () {
        var message = txtMessage.text.toString()
        val shareIntent = Intent(Intent.ACTION_SEND)
        with(shareIntent) {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "ShareIt")
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startActivity(shareIntent)
    }

    private fun handleShareImage () {
        if(imageURI == null) return;

        val shareIntent = Intent(Intent.ACTION_SEND)
        with(shareIntent) {
            type = "image/*"
            putExtra(Intent.EXTRA_SUBJECT, "ShareIt")
            putExtra(Intent.EXTRA_STREAM, imageURI)
        }
        startActivity(shareIntent)
    }

    private fun handleChooseImage () {
        val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageURI =  data?.data;
            buttonChooseImage.setImageURI(imageURI)
        }
    }
}
