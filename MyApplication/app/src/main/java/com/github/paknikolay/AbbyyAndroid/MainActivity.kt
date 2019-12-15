package com.github.paknikolay.AbbyyAndroid

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), NoteAdapter.Listener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = resources.getString( resources.getIdentifier("notes", "string", packageName));

        var recyclerView = findViewById<RecyclerView>(R.id.NoteRecyclerView)

        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setHasFixedSize(true)
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 15)

        val adapter = NoteAdapter()
        recyclerView.setAdapter(adapter)
        adapter.setNoteList(NoteRepository.getTextList())
        adapter.setListener(this)

//        var data = findViewById<TextView>(R.id.data);
//        val formater = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentDate = formater.format(Date())
//        data.text = currentDate;
//
//        var cardView = findViewById<CardView>(R.id.cardView);
//        cardView.setOnClickListener{

        }

    override fun onNoteClick( cardView:CardView, id: Long ) {
        startAnimationViaAnimator( cardView );
        val intent = Intent(this, NoteActivity::class.java );
        intent.putExtra("id", id);
        startActivity(intent)
    }

    private fun startAnimationViaAnimator( cardView:CardView ) {
        val set = AnimatorInflater.loadAnimator( this, R.animator.animation ) as AnimatorSet
        set.setTarget( cardView )
        set.start()
    }
}


