package com.moysa.saveup.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.moysa.saveup.data.AppDatabase.DATABASE_NAME;

/**
 * Created by Sergey Moysa
 */

public class DatabaseCreator {

    private static final Object LOCK = new Object();
    private static DatabaseCreator sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private final AtomicBoolean mInitializing = new AtomicBoolean(true);
    private AppDatabase mDb;

    public synchronized static DatabaseCreator getInstance(Context context) {

        if (sInstance == null) {

            synchronized (LOCK) {

                if (sInstance == null) {

                    sInstance = new DatabaseCreator();
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<Boolean> isDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    public AppDatabase getDatabase() {
        return mDb;
    }

    public void createDb(Context context) {

        Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().getName());

        if (!mInitializing.compareAndSet(true, false)) {
            return; // Already initializing
        }

        mIsDatabaseCreated.setValue(false); //Trigger and update progress bar

        new AsyncTask<Context, Void, Void>() {

            @Override
            protected Void doInBackground(Context... params) {

                Log.d("DatabaseCreator", "Starting bg job " + Thread.currentThread().getName());

                Context context = params[0].getApplicationContext();

                //reset db
//                context.deleteDatabase(DATABASE_NAME);

                //build db
                AppDatabase db = Room.databaseBuilder(
                        context, AppDatabase.class, DATABASE_NAME).build();

                //just fancy delay
                addDelay();

                Log.d("DatabaseCreator", "Filling db from " + Thread.currentThread().getName());

                //filling db with dump values
//                DatabaseInitUtil.initializeDb(db);

                mDb = db;

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                mIsDatabaseCreated.setValue(true);
            }
        }.execute(context);
    }

    private void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }
}
