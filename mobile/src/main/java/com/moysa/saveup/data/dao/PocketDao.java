package com.moysa.saveup.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.moysa.saveup.data.entity.PocketEntity;

import java.util.List;

/**
 * Created by Sergey Moysa
 */

@Dao
public interface PocketDao {

    @Query("SELECT * FROM pockets WHERE userId = :userId")
    LiveData<List<PocketEntity>> loadPockets(int userId);

    @Query("SELECT * FROM pockets WHERE userId = :userId")
    List<PocketEntity> loadPocketsSync(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<PocketEntity> pockets);

    @Query("SELECT * FROM pockets WHERE id = :pocketId")
    LiveData<PocketEntity> loadPocket(int pocketId);

    @Query("SELECT * FROM pockets WHERE id = :pocketId")
    PocketEntity loadPocketSync(int pocketId);

    @Delete
    int delete(PocketEntity... pockets);

    @Update(onConflict = OnConflictStrategy.ABORT)
    int update(PocketEntity... pockets);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(PocketEntity pocket);
}
