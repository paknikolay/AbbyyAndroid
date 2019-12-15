package com.github.paknikolay.AbbyyAndroid;

import android.app.Application;

public class App : Application() {
    override fun onCreate() {
        super.onCreate();
        NoteRepository.initialize(this);
    }
}
