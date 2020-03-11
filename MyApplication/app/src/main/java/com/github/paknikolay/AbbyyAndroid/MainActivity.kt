package com.github.paknikolay.AbbyyAndroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.listNoteContainer, NoteListFragment.newInstance("ListNoteFragment"), NoteListFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    fun showDetailFragment(name: String, id:Long) {
        if (supportFragmentManager.findFragmentByTag(NoteFragment.TAG) != null) { // Если на экране уже есть фрагмент с деталями, то надо его убрать перед показом нового
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.noteContainer, NoteFragment.newInstance(name, id), NoteFragment.TAG)
            .addToBackStack(null)
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


