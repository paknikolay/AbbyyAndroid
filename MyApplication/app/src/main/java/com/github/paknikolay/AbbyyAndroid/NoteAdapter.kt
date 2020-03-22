package com.github.paknikolay.AbbyyAndroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.paknikolay.AbbyyAndroid.db.Note

import java.util.ArrayList

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    interface Listener {
        fun onNoteClick(cardView: CardView, id: Long)
    }

    private var noteList: List<Note> = mutableListOf()
    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun setNoteList(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val view = layoutInflater.inflate(
            R.layout.note_list_item,
            parent,
            false
        )
        return NoteViewHolder(view, listener!!)//.?
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList.get(position))
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}
