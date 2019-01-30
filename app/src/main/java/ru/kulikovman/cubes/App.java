package ru.kulikovman.cubes;

import android.app.Application;
import android.content.Context;


public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App get() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
