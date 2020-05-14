package com.github.paknikolay.AbbyyAndroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.paknikolay.AbbyyAndroid.App;
import com.github.paknikolay.AbbyyAndroid.ImageIdHolder;
import com.github.paknikolay.AbbyyAndroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.UUID;
import com.squareup.picasso.Picasso;

public class NoteContract {

    public static final String TABLE_NAME = "note_table";

    public interface Columns extends BaseColumns {
        String ID = "id";
        String TEXT = "text";
        String DATE =  "date";
        String IMAGE_PATH= "image_path";
    }

    private NoteContract() {
        // Утилитный класс
    }

    public static void createTable(@NonNull final SQLiteDatabase db, Context context) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + " ( "
                        + Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Columns.DATE + " INTEGER NOT NULL,"
                        + Columns.TEXT + "  TEXT NOT NULL,"
                        + Columns.IMAGE_PATH + " TEXT"
                        + ");"
        );

        for (int i = 0; i < 12; i++) {
            long date =  System.currentTimeMillis();
            String text = "shiba" + i;

            File imagePath = new File(App.getContext().getFilesDir(), Integer.toString(i));
            Bitmap image = BitmapFactory.decodeStream(context.getResources().openRawResource(
                    ImageIdHolder.getImageId(i)));
            App.saveImage(imagePath, image);

            new NoteRepository(App.getDatabaseHolder()).create(new Note(date, text, imagePath.getPath(), i), db);

        }
    }
}
