package com.github.paknikolay.AbbyyAndroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.ImageCaptureError
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.util.*


class NoteAddActivity : AppCompatActivity(), ImageCapture.OnImageSavedListener {
    private val CAMERA_REQUEST_CODE = 0

    private var cameraView: CameraView? = null
    private var takePictureButton: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.node_add_activity)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, R.string.need_permission, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.permission_in_settings, Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun startCamera() {
        cameraView = findViewById(R.id.cameraView)
        cameraView?.setCaptureMode(CameraView.CaptureMode.IMAGE)
        cameraView?.bindToLifecycle(this as LifecycleOwner)
        takePictureButton = findViewById(R.id.takePictureButton)
        takePictureButton?.setOnClickListener( {
            cameraView?.takePicture(
                generatePictureFile()!!,
                AsyncTask.SERIAL_EXECUTOR,
                this
            )
        })
    }

    override fun onImageSaved(file: File) {
        val t = 0
    }

    override fun onError(
        imageCaptureError: ImageCaptureError,
        message: String,
        cause: Throwable?
    ) {
        finish()
    }

    private fun generatePictureFile(): File? {
        return File(filesDir, UUID.randomUUID().toString())
    }
}


