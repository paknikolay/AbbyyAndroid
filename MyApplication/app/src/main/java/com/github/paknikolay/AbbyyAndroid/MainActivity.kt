package com.github.paknikolay.AbbyyAndroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository


class MainActivity : AppCompatActivity() {
    private var noteRepository : NoteRepository? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteRepository = NoteRepository(App.getDatabaseHolder())

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

    fun getNoteRepository() : NoteRepository? {
        return noteRepository
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}


