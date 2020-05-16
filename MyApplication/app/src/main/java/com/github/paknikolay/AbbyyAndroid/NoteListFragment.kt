package com.github.paknikolay.AbbyyAndroid;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.paknikolay.AbbyyAndroid.db.Note
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository;
import kotlinx.coroutines.*
import java.io.File
import java.lang.Thread.sleep

class NoteListFragment:Fragment(), NoteAdapter.Listener, NoteAdapter.MenuListener {

    var dialog : Dialog? = null

    companion object {
        val TAG:String = "NoteListFragment"

        val NAME_KEY:String = "NAME_KEY";

        fun newInstance(@NonNull name:String) : Fragment {
            val fragment:Fragment = NoteListFragment();
            val arguments:Bundle = Bundle();

            arguments.putString(NAME_KEY, name);
            fragment.setArguments(arguments);
            return fragment;
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View?
    {
        return inflater.inflate(R.layout.note_list_fragment, container, false)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.NoteRecyclerView)

        if (activity?.resources?.getBoolean(R.bool.isOneColmn) != false)
        {
            recyclerView.setLayoutManager(LinearLayoutManager(activity))
        }
        else
        {
            val mLayoutManager = GridLayoutManager(activity, 2)
            recyclerView.setLayoutManager(mLayoutManager)
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 15)

        val name = getArguments()?.getString(NAME_KEY)

        val addNoteButton = view.findViewById<ImageView>(R.id.addNoteButton)
        addNoteButton.setOnClickListener( { v : View ->
            val intent = Intent(activity, NoteAddActivity::class.java );
            startActivity(intent)
        } )
    }

    @Override
    override fun onResume() {
        super.onResume()
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.NoteRecyclerView)
        loadData(recyclerView, this, this)
    }

    fun loadData(recyclerView:RecyclerView,
                 listener: NoteAdapter.Listener,
                 menuListener: NoteAdapter.MenuListener)
            = CoroutineScope(Dispatchers.Main).launch {

        val task = async(Dispatchers.IO) {
            (activity as MainActivity).getNoteRepository()?.loadAll()
        }
        task.join()
        val adapter = NoteAdapter()
        recyclerView.setAdapter(adapter)
        adapter.setNoteList(NoteRepository.getTextList())
        adapter.setListener(listener)
        adapter.setMenuListener(menuListener)
    }

    override fun onNoteClick(cardView:CardView, id:Long) {
        startAnimationViaAnimator(cardView)
        (getActivity() as MainActivity).showDetailFragment("NoteFragment", id)
    }

    override fun onMenuClick(view: View, note: Note) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.popup_menu_first -> {
                    val file = File(note.imagePath)
                    val uri = FileProvider.getUriForFile(context!!, "com.github.paknikolay.AbbyyAndroid", file);
                    val intent = ShareCompat.IntentBuilder.from(activity)
                    .setType("bynary/binaryImage")
                    .setStream(uri)
                    .setChooserTitle("binaryImage")
                    .createChooserIntent()
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    context!!.startActivity(intent);
                    Toast.makeText(context, R.string.popup_menu_first, Toast.LENGTH_SHORT).show()
                }

                R.id.popup_menu_third -> {
                    dialog = AlertDialog
                        .Builder(context!!)
                        .setTitle(R.string.delete)
                        .setMessage(R.string.confirmation)
                        .setPositiveButton(R.string.yes, DialogInterface.OnClickListener() {
                                dialog, which ->

                            NoteRepository(App.getDatabaseHolder()).delete(note, null)
                            Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show()

                        })
                        .setNegativeButton(R.string.no, DialogInterface.OnClickListener() {
                                dialog, which ->
                            Toast.makeText(context, R.string.canceled, Toast.LENGTH_SHORT).show()
                        })
                        .create()

                    dialog?.show()
                }
                R.id.popup_menu_second -> Toast.makeText(context, R.string.popup_menu_second, Toast.LENGTH_SHORT).show()

            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }
    private fun startAnimationViaAnimator(cardView:CardView) {
        val set = AnimatorInflater.loadAnimator(getActivity(), R.animator.animation) as AnimatorSet
        set.setTarget(cardView)
        set.start()
    }

}