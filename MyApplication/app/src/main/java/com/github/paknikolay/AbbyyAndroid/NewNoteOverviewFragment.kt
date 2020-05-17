package com.github.paknikolay.AbbyyAndroid;

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.github.paknikolay.AbbyyAndroid.db.Note
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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

    fun createNote(note:Note) = CoroutineScope(Dispatchers.Main).launch {
        val task = async(Dispatchers.IO) {
            val id = NoteRepository(App.getDatabaseHolder()).create(note, null)

        }
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var filePath = getArguments()?.getString("file")
        var file = File(filePath)
        var imageView = getView()?.findViewById<ImageView>(R.id.cameraImage)
        Picasso.get().load(file).fit().centerInside().into(imageView)

        val bitmap = BitmapFactory.decodeFile(filePath)
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        val result = detector.processImage(image)
            .addOnSuccessListener { firebaseVisionDocumentText ->
                val text = firebaseVisionDocumentText.text.toString()
                val date = System.currentTimeMillis()
                //сохраним заметку
                createNote(Note(date, text, filePath!!) )
                //покажем текст заметки
                getView()?.findViewById<TextView>(R.id.textFromCamera)?.setText( text )
            }
            .addOnFailureListener { e ->
               e.printStackTrace()
            }


    }
}