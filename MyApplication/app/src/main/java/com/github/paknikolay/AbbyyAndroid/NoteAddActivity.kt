package com.github.paknikolay.AbbyyAndroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NoteAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.node_add_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.cameraContainer, CameraFragment.newInstance("Cameraragment"), CameraFragment.TAG)
                .commit()
        }
    }

    fun showOverviewFragment(name: String, file:String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.cameraContainer, NewNoteOverviewFragment.newInstance(name, file), NewNoteOverviewFragment.TAG)
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


