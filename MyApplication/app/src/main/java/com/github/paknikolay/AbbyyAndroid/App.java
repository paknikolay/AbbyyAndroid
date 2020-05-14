package com.github.paknikolay.AbbyyAndroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import com.github.paknikolay.AbbyyAndroid.db.DatabaseHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static DatabaseHolder databaseHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        databaseHolder = new DatabaseHolder(context);
        ImageIdHolder.initialise(context);
    }

    // Способ получения контекста из любой части приложения
    public static Context getContext() {
        return context;
    }

    public static DatabaseHolder getDatabaseHolder() {
        return databaseHolder;
    }

    public static boolean isFileExist(String fname){
        File file = context.getFileStreamPath(fname);
        return file.exists();
    }

    public static void saveImage(File filePath, Bitmap image) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
