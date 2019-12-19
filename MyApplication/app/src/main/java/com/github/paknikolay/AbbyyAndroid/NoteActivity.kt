package com.github.paknikolay.AbbyyAndroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val id = intent.getLongExtra("id", 0);

        title = resources.getString(R.string.detailedNoteActivityTitle);
        val imageView = findViewById<ImageView>(R.id.note_image);
        val textView = findViewById<TextView>(R.id.note_text);

        imageView.setImageDrawable(getDrawable(NoteRepository.GetNoteById(id).drawableId));
        textView.setText(NoteRepository.GetNoteById(id).text)
    }
}
