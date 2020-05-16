package com.github.paknikolay.AbbyyAndroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NoteRepository {
    private static Map<Long, Note> textList = new HashMap<>();

    private DatabaseHolder databaseHolder;

    public NoteRepository(@NonNull final DatabaseHolder databaseHolder) {
        this.databaseHolder = databaseHolder;
    }

    public long create(@NonNull final Note note, final SQLiteDatabase db) {
        long id = note.getId();
        try {

            SQLiteDatabase database;
            if (db == null) {
                database = databaseHolder.open();
            } else {
                database = db;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(NoteContract.Columns.TEXT, note.getText());
            if( id != -1 ) {
                contentValues.put(NoteContract.Columns.ID, note.getId());
            }
          //  contentValues.put(NoteContract.Columns.ID, 13);
            contentValues.put(NoteContract.Columns.DATE, note.getDate());
            contentValues.put(NoteContract.Columns.IMAGE_PATH, note.getImagePath());

            id = database.insertOrThrow(NoteContract.TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db == null) {
                databaseHolder.close();
            }
        }
        return id;
    }

    public void delete(@NonNull final Note note, final SQLiteDatabase db) {
        try {

            SQLiteDatabase database;
            if (db == null) {
                database = databaseHolder.open();
            } else {
                database = db;
            }

            new File(note.getImagePath()).delete();

            database.delete(NoteContract.TABLE_NAME, NoteContract.Columns.ID + "=" +
                    Long.toString(note.getId()), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db == null) {
                databaseHolder.close();
            }
        }
    }

    public void loadAll() {
        Cursor cursor = null;
        try {
            SQLiteDatabase database = databaseHolder.open();

            cursor = database.query(
                    NoteContract.TABLE_NAME,
                    new String[] {NoteContract.Columns.ID,
                            NoteContract.Columns.TEXT, NoteContract.Columns.DATE,
                            NoteContract.Columns.IMAGE_PATH},
                    null,
                    null,
                    null,
                    null,
                    null
            );

            while (cursor.moveToNext()) {
                Long date = cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.DATE));
                Note note = new Note(
                        date,
                        cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT)),
                        cursor.getString(cursor.getColumnIndex(NoteContract.Columns.IMAGE_PATH)),
                        cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.ID))
                );
                textList.put( note.getId(), note );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            databaseHolder.close();
        }
    }

    public static List<Note> getTextList() {
        return new ArrayList(textList.values());
    }

    public static void resetStorageCache() {
        textList.clear();
    }

    public static Note getNoteById(final long id) {
        return textList.get(id);
    }
}
