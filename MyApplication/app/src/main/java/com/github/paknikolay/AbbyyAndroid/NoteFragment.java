package com.github.paknikolay.AbbyyAndroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository;


public class NoteFragment extends Fragment {

    public static final String TAG = "NoteFragment";

    private static final String NAME_KEY = "NAME_KEY";

    public static Fragment newInstance(@NonNull final String name, Long id) {
        final Fragment fragment = new NoteFragment();

        final Bundle arguments = new Bundle();
        arguments.putString(NAME_KEY, name);
        arguments.putLong("id", id);

        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Long id = getArguments().getLong("id", 0);

        String title = getResources().getString(R.string.detailedNoteActivityTitle);
        ImageView imageView = view.findViewById(R.id.note_image);
        TextView textView = view.findViewById(R.id.note_text);

        imageView.setImageDrawable(getResources().getDrawable(NoteRepository.GetNoteById(id).getDrawableId(), null));
        textView.setText(NoteRepository.GetNoteById(id).getText());

        final String name = getArguments().getString(NAME_KEY);
    }

}
