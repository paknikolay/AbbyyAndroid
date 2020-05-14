package com.github.paknikolay.AbbyyAndroid;

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.github.paknikolay.AbbyyAndroid.db.Note
import com.github.paknikolay.AbbyyAndroid.db.NoteContract
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository
import com.squareup.picasso.Picasso
import java.io.File

class NewNoteOverviewFragment:Fragment() {
    companion object {
        val TAG:String = "NewNoteOverviewFragment"

        val NAME_KEY:String = "NAME_KEY";

        fun newInstance(@NonNull name:String, file:String) : Fragment {
            val fragment:Fragment = NewNoteOverviewFragment()
            val arguments:Bundle = Bundle()
            arguments.putString( "file", file )
            arguments.putString(NAME_KEY, name)
            fragment.setArguments(arguments)
            return fragment
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View?
    {
        return inflater.inflate(R.layout.new_note_overview_fragment, container, false)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var filePath = getArguments()?.getString("file")
        var file = File(filePath)
        var imageView = getView()?.findViewById<ImageView>(R.id.cameraImage)
        Picasso.get().load(file).fit().centerInside().into(imageView)

        val date = System.currentTimeMillis()
        val text = "shibaNew"
        //сохраним заметку
        val id = NoteRepository(App.getDatabaseHolder()).create(Note(date, text, filePath!!), null)
        getView()?.findViewById<TextView>(R.id.textFromCamera)?.setText( id.toString() )

    }
}