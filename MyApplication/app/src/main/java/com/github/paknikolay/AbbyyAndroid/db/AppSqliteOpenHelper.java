package com.github.paknikolay.AbbyyAndroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.paknikolay.AbbyyAndroid.App;


public class AppSqliteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SampleDatabase.db";
    private static final int VERSION = 1;
    private static Context context = null;

    public AppSqliteOpenHelper(@Nullable final Context context) {
        super(App.getContext(), DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase db) {
        NoteContract.createTable(db, App.getContext());
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}
