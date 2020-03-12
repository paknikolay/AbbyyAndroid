package com.github.paknikolay.AbbyyAndroid;

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.paknikolay.AbbyyAndroid.db.Note

import java.text.SimpleDateFormat

class NoteViewHolder(
    private var itemView_: View,
    private var listener: NoteAdapter.Listener
) : RecyclerView.ViewHolder(itemView_) {

    private var cardView: CardView = itemView_.findViewById(R.id.cardView);
    private var noteNameTextView: TextView = itemView_.findViewById(R.id.note_list_text);
    private var dataView: TextView = itemView_.findViewById<TextView>(R.id.note_list_data);
    private var imageView: ImageView = itemView_.findViewById<ImageView>(R.id.note_list_image)

    private var id: Long = 0;

    fun bind(note: Note) {
        noteNameTextView.setText(note.text);
        id = note.id;
        val formater = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = formater.format(note.data)
        dataView.text = currentDate;
        cardView.setOnClickListener { listener.onNoteClick(cardView, id) };
        imageView.setImageDrawable(itemView_.resources.getDrawable(note.drawableId));
    }
}
