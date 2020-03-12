package com.github.paknikolay.AbbyyAndroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteContract {

    public static final String TABLE_NAME = "note_table";

    public interface Columns extends BaseColumns {
        String ID = "id";
        String TEXT = "text";
        String DATE =  "date";
        String DRAWABLE_ID = "drawable_id";
    }

    private NoteContract() {
        // Утилитный класс
    }

    public static void createTable(@NonNull final SQLiteDatabase db, Context context) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + " ( "
                        + Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Columns.ID + " INTEGER NOT NULL,"
                        + Columns.DATE + " TEXT NOT NULL,"
                        + Columns.TEXT + "  TEXT NOT NULL,"
                        + Columns.DRAWABLE_ID + " INTEGER NOT NULL"
                        + ");"
        );

        for (int i = 0; i < 12; i++) {
            String date = "22.02.1879 11:02:52";
            String text = "shiba" + i;
            String imageName = "image_" + i + ".jpeg";

            int imageId = context.getResources().getIdentifier("image_" + Long.toString(i), "drawable", context.getPackageName());


            db.execSQL("INSERT INTO " + TABLE_NAME + "("
                            + Columns.ID + ","
                            + Columns.TEXT + ","
                            + Columns.DRAWABLE_ID + ","
                            +  Columns.DATE + ")"
                    + " VALUES("
                    + i + ","
                    + "\"" + text + "\","
                    + imageId + ","
                    + "\"" + date + "\");");
        }
    }
}
