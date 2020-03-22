package com.github.paknikolay.AbbyyAndroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.paknikolay.AbbyyAndroid.ImageIdHolder;
import com.github.paknikolay.AbbyyAndroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteContract {

    public static final String TABLE_NAME = "note_table";

    public interface Columns extends BaseColumns {
        String ID = "id";
        String TEXT = "text";
        String DATE =  "date";
        String IMAGE_INDX = "image_indx";
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
                        + Columns.DATE + " INTEGER NOT NULL,"
                        + Columns.TEXT + "  TEXT NOT NULL,"
                        + Columns.IMAGE_INDX + " INTEGER NOT NULL"
                        + ");"
        );

        for (int i = 0; i < 12; i++) {
            long date =  System.currentTimeMillis();
            String text = "shiba" + i;
            int imageIndx = i;

            db.execSQL("INSERT INTO " + TABLE_NAME + "("
                            + Columns.ID + ","
                            + Columns.TEXT + ","
                            + Columns.IMAGE_INDX + ","
                            +  Columns.DATE + ")"
                    + " VALUES("
                    + i + ","
                    + "\"" + text + "\","
                    + imageIndx + ","
                    + "\"" + date + "\");");
        }
    }
}
