package com.moysa.saveup.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.moysa.saveup.data.converter.DateConverter;
import com.moysa.saveup.data.dao.PocketDao;
import com.moysa.saveup.data.dao.TransactionDao;
import com.moysa.saveup.data.dao.UserDao;
import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.entity.UserEntity;

/**
 * Created by Sergey Moysa
 */

@Database(
        entities = {
                UserEntity.class,
                PocketEntity.class,
                TransactionEntity.class
        },
        version = 1
)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "saveup_db";

    public abstract UserDao userDao();

    public abstract PocketDao pocketDao();

    public abstract TransactionDao transactionDao();
}
