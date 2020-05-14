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
import com.github.paknikolay.AbbyyAndroid.db.Note
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository
import java.io.File
import java.util.*


class NoteAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.node_add_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.cameraContainer, CameraFragment.newInstance("Cameraragment"), CameraFragment.TAG)
               // .addToBackStack(null)
                .commit()
        }
    }

    fun showOverviewFragment(name: String, file:String) {
        supportFragmentManager
            .beginTransaction()
           // .replace(R.id.cameraContainer, NoteFragment.newInstance(name, 2), NoteFragment.TAG)
            .replace(R.id.cameraContainer, NewNoteOverviewFragment.newInstance(name, file), NewNoteOverviewFragment.TAG)
  //          .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}


