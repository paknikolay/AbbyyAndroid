package com.github.paknikolay.AbbyyAndroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NoteRepository {
    private static Map<Long, Note> textList = new HashMap<>();

    private DatabaseHolder databaseHolder;

    public NoteRepository(@NonNull final DatabaseHolder databaseHolder) {
        this.databaseHolder = databaseHolder;
    }

    public void create(@NonNull final Note note) {
        try {
            SQLiteDatabase database = databaseHolder.open();

            ContentValues contentValues = new ContentValues();
            contentValues.put(NoteContract.Columns.TEXT, note.getText());
            contentValues.put(NoteContract.Columns.ID, note.getId());
            contentValues.put(NoteContract.Columns.DATE, note.getData().toString());
            contentValues.put(NoteContract.Columns.DRAWABLE_ID, note.getDrawableId());

            database.insert(NoteContract.TABLE_NAME, null, contentValues);
        } finally {
            databaseHolder.close();
        }
    }

    public void loadAll() {
        Cursor cursor = null;
        try {
            SQLiteDatabase database = databaseHolder.open();

            cursor = database.query(
                    NoteContract.TABLE_NAME,
                    new String[] {NoteContract.Columns._ID, NoteContract.Columns.ID,
                            NoteContract.Columns.TEXT, NoteContract.Columns.DATE,
                            NoteContract.Columns.DRAWABLE_ID},
                    null,
                    null,
                    null,
                    null,
                    null
            );

            while (cursor.moveToNext()) {
                Note note = new Note();

                note.setId(cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.ID)));
                String date = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.DATE));
                Date data = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(date);
                note.setData(data);
                note.setDrawableId(cursor.getInt(cursor.getColumnIndex(NoteContract.Columns.DRAWABLE_ID)));
                note.setText(cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT)));

                textList.put( note.getId(), note );
            }
        } catch (ParseException e) {
            e.printStackTrace();
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

    public static Note GetNoteById(final long id) {
        return textList.get(id);
    }
}
