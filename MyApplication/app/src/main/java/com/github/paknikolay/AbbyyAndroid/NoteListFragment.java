package com.github.paknikolay.AbbyyAndroid;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public class NoteListFragment extends Fragment implements NoteAdapter.Listener  {
    public static final String TAG = "NoteListFragment";

    private static final String NAME_KEY = "NAME_KEY";

    public static Fragment newInstance(@NonNull final String name) {
        final Fragment fragment = new NoteListFragment();

        final Bundle arguments = new Bundle();
        arguments.putString(NAME_KEY, name);

        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.NoteRecyclerView);

        if(getActivity().getResources().getBoolean(R.bool.isOneColmn)) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 15);

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setNoteList(NoteRepository.getTextList());
        adapter.setListener(this);


        final String name = getArguments().getString(NAME_KEY);

    }

    @Override
    public void onNoteClick(CardView cardView, long id) {
        startAnimationViaAnimator(cardView);

        ( ( MainActivity )getActivity() ) .showDetailFragment("NoteFragment", id);

    }

    private void startAnimationViaAnimator(CardView cardView) {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation);
        set.setTarget(cardView);
        set.start();
    }
    
}
