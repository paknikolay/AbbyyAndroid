package com.github.paknikolay.AbbyyAndroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import java.io.File;

import java.util.*;

class NoteRepository (
    private var databaseHolder : DatabaseHolder
){
    companion object {
        val textList = HashMap<Long, Note>()
        fun getTextList():List<Note> {
            return ArrayList(textList.values);
        }

        fun getNoteById(id : Long) :Note {
            return textList.get(id)!!
        }
    }

    fun create(@NonNull note:Note, db : SQLiteDatabase?) : Long {
        var id = note.id
        try {
            var database : SQLiteDatabase? =  null
            if (db == null) {
                database = databaseHolder.open();
            } else {
                database = db;
            }
            val contentValues:ContentValues  = ContentValues()
            contentValues.put(NoteContract.Columns.TEXT, note.text)
            if( id != -1L ) {
                contentValues.put(NoteContract.Columns.ID, note.id);
            }
            contentValues.put(NoteContract.Columns.DATE, note.date);
            contentValues.put(NoteContract.Columns.IMAGE_PATH, note.imagePath);

            id = database!!.insertOrThrow(NoteContract.TABLE_NAME, null, contentValues);

            val newNote:Note  = Note(note.date, note.text, note.imagePath, id);
            textList.put(id, newNote);
        } catch (e:Exception) {
            e.printStackTrace();
        } finally {
            if (db == null) {
                databaseHolder.close();
            }
        }
        return id
    }

    fun update(@NonNull note:Note, db:SQLiteDatabase?) {
        assert (note.id != -1L);
        try {
            var database : SQLiteDatabase? =  null
            if (db == null) {
                database = databaseHolder.open();
            } else {
                database = db;
            }
            val contentValues:ContentValues  = ContentValues()
            contentValues.put(NoteContract.Columns.TEXT, note.text)
            contentValues.put(NoteContract.Columns.ID, note.id)

            contentValues.put(NoteContract.Columns.DATE, note.date)
            contentValues.put(NoteContract.Columns.IMAGE_PATH, note.imagePath)

            val affected = database!!.update(NoteContract.TABLE_NAME, contentValues, NoteContract.Columns.ID + "= ?", arrayOf(note.id.toString()))
            assert (affected == 1);
            textList.remove(note.id);
            textList.put(note.id, note);
        } catch (e:Exception) {
            e.printStackTrace();
        } finally {
            if (db == null) {
                databaseHolder.close()
            }
        }
    }

    fun delete(@NonNull id:Long, db:SQLiteDatabase?) {
        try {

            var database : SQLiteDatabase? =  null
            if (db == null) {
                database = databaseHolder.open();
            } else {
                database = db;
            }

            File(NoteRepository.getNoteById(id).imagePath).delete();

            database!!.delete(NoteContract.TABLE_NAME, NoteContract.Columns.ID + "=" +
                    id.toString(), null);
            textList.remove(id);
        } catch (e:Exception) {
            e.printStackTrace()
        } finally {
            if (db == null) {
                databaseHolder.close();
            }
        }
    }

    suspend fun loadAll() {
        var cursor : Cursor?  = null;
        try {
            val database : SQLiteDatabase  = databaseHolder.open()

            cursor = database.query(
                    NoteContract.TABLE_NAME,
                arrayOf(NoteContract.Columns.ID,
                            NoteContract.Columns.TEXT, NoteContract.Columns.DATE,
                            NoteContract.Columns.IMAGE_PATH),
                    null,
                    null,
                    null,
                    null,
                    null
            );

            while (cursor.moveToNext()) {
                val date = cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.DATE));
                val note = Note(
                        date,
                        cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT)),
                        cursor.getString(cursor.getColumnIndex(NoteContract.Columns.IMAGE_PATH)),
                        cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.ID))
                );
                textList.put( note.id, note );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            databaseHolder.close();
        }
    }


}
