package com.github.paknikolay.AbbyyAndroid;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NoteRepository {

    private static Map<Long, Note> textList = new HashMap<>();

    public static void initialize(final Context context) {
        for (long i = 0; i < 12; i++) {
            textList.put(i, new Note( i, context ));
            //textList.put(i, new Note( i, new Date(1024), "sdffdsds", context ));
        }
    }

    public static List<Note> getTextList() {
        return new ArrayList(textList.values());
    }

    public static Note GetNoteById(final long id) {
        return textList.get(id);
    }
}
