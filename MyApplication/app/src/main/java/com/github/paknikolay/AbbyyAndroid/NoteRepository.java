package com.github.paknikolay.AbbyyAndroid;

import android.content.Context;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class NoteRepository {

    private static Map<Long, Note> textList = new HashMap<>();

    public static void initialize(final Context context) throws ParseException {
        for (long i = 0; i < 12; i++) {
            //странно, но бд пока у нас нет, так, что пока все в ресурсах
            //находим в ресурсах строку с датой
            int dateId = context.getResources().getIdentifier("date_" + Long.toString(i), "string", context.getPackageName());
            Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(context.getResources().getString(dateId));
            //находим в ресурсвх строку с текстом
            int stringId = context.getResources().getIdentifier("text_" + Long.toString(i), "string", context.getPackageName());
            String text = context.getResources().getString(stringId);

            int imageId = context.getResources().getIdentifier("image_" + Long.toString(i), "drawable", context.getPackageName());

            textList.put(i, new Note(i, date, text, imageId));
        }
    }

    public static List<Note> getTextList() {
        return new ArrayList(textList.values());
    }

    public static Note GetNoteById(final long id) {
        return textList.get(id);
    }
}
