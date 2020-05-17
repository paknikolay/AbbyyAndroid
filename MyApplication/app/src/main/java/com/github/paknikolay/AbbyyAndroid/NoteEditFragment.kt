package com.github.paknikolay.AbbyyAndroid;

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment;
import com.github.paknikolay.AbbyyAndroid.db.Note
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

import java.io.File;


class NoteEditFragment : Fragment() {
    var dialog : Dialog? = null
    companion object {
        val TAG:String = "NoteEditFragment"

        val NAME_KEY:String = "NAME_KEY"

        fun newInstance(@NonNull name:String , id:Long) : Fragment {
            val fragment = NoteEditFragment()

            val arguments = Bundle ()
            arguments.putString(NAME_KEY, name);
            arguments.putLong("id", id);

            fragment.setArguments(arguments);

            return fragment;
        }
    }

    @Nullable
    override fun onCreateView(
        @NonNull inflater:LayoutInflater,
        @Nullable container:ViewGroup?,
        @Nullable savedInstanceState:Bundle?) : View {
        return inflater.inflate(R.layout.note_edit_fragment, container, false);
    }

    override fun onViewCreated(@NonNull view:View, @Nullable savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        val id = getArguments()!!.getLong("id", 0);

        val title = getResources().getString(R.string.detailedNoteActivityTitle);
        val imageView = view.findViewById<ImageView>(R.id.note_image);
        val editText = view.findViewById<EditText>(R.id.note_text_edit);
        val saveButton = view.findViewById<Button>(R.id.note_edit_save_botton);

        val file = File(NoteRepository.getNoteById(id).imagePath);
        Picasso.get().load(file).fit().centerInside().into(imageView);

        val text = NoteRepository.getNoteById(id).text
        editText.setText(text);

        saveButton.setOnClickListener { buttonView ->
            dialog = AlertDialog
                .Builder(context!!)
                .setTitle(R.string.save)
                .setMessage(R.string.confirmation)
                .setPositiveButton(R.string.yes, {
                        dialog, which ->
                    val note = NoteRepository.getNoteById(id)
                    val newNote  = Note(note.date, editText.text!!.toString(), note.imagePath, note.id)

                    NoteRepository(App.getDatabaseHolder()).update(newNote, null)
                    Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
                    ((activity as MainActivity).getFragment(NoteListFragment.TAG) as NoteListFragment?)?.updateData()

                })
                .setNegativeButton(R.string.no, {
                        dialog, which ->
                    Toast.makeText(context, R.string.canceled, Toast.LENGTH_SHORT).show()
                })
                .create()
            dialog?.show()
        }



    }

}
