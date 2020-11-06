package com.example.shareit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
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

    private fun handleShareImage (){
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    handleShareImageExecute();
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText( applicationContext, "É necessário garantir permissão para acessarmos os arquivos!", Toast.LENGTH_LONG).show()
                }
            })
            .check()
    }

    private fun handleShareImageExecute () {
        if(imageURI == null) return;

        val shareIntent = Intent(Intent.ACTION_SEND)
        with(shareIntent) {
            type = "image/*"
            putExtra(Intent.EXTRA_SUBJECT, "ShareIt")
            putExtra(Intent.EXTRA_STREAM, imageURI)
        }
        startActivity(shareIntent)
    }

    private fun handleChooseImage (){
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    handleChooseImageExecute();
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText( applicationContext, "É necessário garantir permissão para acessarmos os arquivos!", Toast.LENGTH_LONG).show()
                }
            })
            .check()
    }

    private fun handleChooseImageExecute () {
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
