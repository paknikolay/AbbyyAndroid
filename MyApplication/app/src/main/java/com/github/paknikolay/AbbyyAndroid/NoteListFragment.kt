package com.github.paknikolay.AbbyyAndroid;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository;
import kotlinx.coroutines.*
import java.lang.Thread.sleep

class NoteListFragment:Fragment(), NoteAdapter.Listener {
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
        loadData(recyclerView, this)

        val name = getArguments()?.getString(NAME_KEY)
    }

    fun loadData(recyclerView:RecyclerView, listener: NoteAdapter.Listener) = CoroutineScope(Dispatchers.Main).launch {

        val task = async(Dispatchers.IO) {
            (activity as MainActivity).getNoteRepository()?.loadAll()
        }
        task.join()
        val adapter = NoteAdapter()
        recyclerView.setAdapter(adapter)
        adapter.setNoteList(NoteRepository.getTextList())
        adapter.setListener(listener)
    }

    override fun onNoteClick(cardView:CardView, id:Long) {
        startAnimationViaAnimator(cardView)
        (getActivity() as MainActivity).showDetailFragment("NoteFragment", id)
    }

    private fun startAnimationViaAnimator(cardView:CardView) {
        val set = AnimatorInflater.loadAnimator(getActivity(), R.animator.animation) as AnimatorSet
        set.setTarget(cardView)
        set.start()
    }

}