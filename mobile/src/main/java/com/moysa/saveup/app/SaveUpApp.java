package com.moysa.saveup.app;

import android.app.Application;

import com.moysa.saveup.data.DatabaseCreator;

/**
 * Created by Sergey Moysa
 */

public class SaveUpApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this);
        databaseCreator.createDb(this);
    }
}
