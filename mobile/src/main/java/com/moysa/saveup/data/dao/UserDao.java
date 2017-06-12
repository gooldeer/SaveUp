package com.moysa.saveup.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.moysa.saveup.data.entity.UserEntity;

import java.util.List;

/**
 * Created by Sergey Moysa
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> loadAllUsers();

    @Query("SELECT * FROM users")
    List<UserEntity> loadAllUsersSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<UserEntity> users);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long insert(UserEntity user);

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<UserEntity> loadUser(int userId);

    @Query("SELECT * FROM users WHERE id = :userId")
    UserEntity loadUserSync(int userId);

    @Delete
    int delete(UserEntity... users);

    @Update(onConflict = OnConflictStrategy.FAIL)
    int update(UserEntity... users);
}
