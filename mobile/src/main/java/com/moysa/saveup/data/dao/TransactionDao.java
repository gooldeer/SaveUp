package com.moysa.saveup.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.moysa.saveup.data.entity.TransactionEntity;

import java.util.List;

/**
 * Created by Sergey Moysa
 */

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM transactions WHERE pocketId = :pocketId")
    LiveData<List<TransactionEntity>> loadTransactions(int pocketId);

    @Query("SELECT * FROM transactions WHERE pocketId = :pocketId")
    List<TransactionEntity> loadTransactionsSync(int pocketId);

    @Query("SELECT * FROM transactions WHERE pocketId = :pocketId")
    Cursor loadTransactionsCursorSync(int pocketId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TransactionEntity> transactions);

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    LiveData<TransactionEntity> loadTransaction(int transactionId);

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    TransactionEntity loadTransactionSync(int transactionId);

    @Delete
    int delete(TransactionEntity... transactions);

    @Update(onConflict = OnConflictStrategy.ABORT)
    int update(TransactionEntity... transactions);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(TransactionEntity transaction);
}
