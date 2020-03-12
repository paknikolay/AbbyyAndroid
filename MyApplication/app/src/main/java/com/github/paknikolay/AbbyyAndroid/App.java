package com.github.paknikolay.AbbyyAndroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import com.github.paknikolay.AbbyyAndroid.db.DatabaseHolder;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static DatabaseHolder databaseHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        databaseHolder = new DatabaseHolder(context);
    }

    // Способ получения контекста из любой части приложения
    public static Context getContext() {
        return context;
    }

    public static DatabaseHolder getDatabaseHolder() {
        return databaseHolder;
    }
}
