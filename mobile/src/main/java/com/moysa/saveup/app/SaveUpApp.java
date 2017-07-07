package com.moysa.saveup.app;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.moysa.saveup.data.AppDatabase;
import com.moysa.saveup.data.DatabaseCreator;

/**
 * Created by Sergey Moysa
 */

public class SaveUpApp extends Application {

    private static SaveUpApp instance;
    private DatabaseCreator mDbCreator;

    public static SaveUpApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        mDbCreator = DatabaseCreator.getInstance(this);
        mDbCreator.createDb(this);
    }

    public LiveData<Boolean> isDbCreated() {
        return mDbCreator.isDatabaseCreated();
    }

    public AppDatabase getDatabase() {
        return mDbCreator.getDatabase();
    }
}
