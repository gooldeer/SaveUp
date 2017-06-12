package com.moysa.saveup.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.moysa.saveup.data.AppDatabase;
import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.utils.LiveDataTestUtil;
import com.moysa.saveup.utils.PocketsGenerator;
import com.moysa.saveup.utils.TransactionsGenerator;
import com.moysa.saveup.utils.UsersGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Sergey Moysa
 */
@RunWith(AndroidJUnit4.class)
public class TransactionDaoTest {

    private AppDatabase mDb;
    private TransactionDao mDao;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.transactionDao();
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }

    @Test
    public void loadTransactions() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        List<TransactionEntity> transactions = TransactionsGenerator.testTransactions(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insertAll(transactions);

        LiveData<List<TransactionEntity>> data = mDao.loadTransactions(pocket.getId());

        assertEquals(transactions, LiveDataTestUtil.getValue(data));
    }

    @Test
    public void loadTransactionsSync() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        List<TransactionEntity> transactions = TransactionsGenerator.testTransactions(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insertAll(transactions);

        assertEquals(transactions, mDao.loadTransactionsSync(pocket.getId()));
    }

    @Test
    public void loadTransactionsCursorSync() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        List<TransactionEntity> transactions = TransactionsGenerator.testTransactions(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insertAll(transactions);

        Cursor cursor = mDao.loadTransactionsCursorSync(pocket.getId());

        assertNotNull(cursor);
        assertEquals(transactions.size(), cursor.getCount());
    }

    @Test
    public void insertAll() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        List<TransactionEntity> transactions = TransactionsGenerator.testTransactions(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insertAll(transactions);

        assertEquals(transactions, mDao.loadTransactionsSync(pocket.getId()));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void insertAllWithoutPocket() throws Exception {

        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        List<TransactionEntity> transactions = TransactionsGenerator.testTransactions(pocket);

//        mDb.userDao().insert(user);
//        mDb.pocketDao().insert(pocket);
        mDao.insertAll(transactions);
    }

    @Test
    public void loadTransaction() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);

        LiveData<TransactionEntity> data = mDao.loadTransaction(transaction.getId());

        assertEquals(transaction, LiveDataTestUtil.getValue(data));
    }

    @Test
    public void loadTransactionSync() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);

        assertEquals(transaction, mDao.loadTransactionSync(transaction.getId()));
    }

    @Test
    public void delete() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);

        int deleted = mDao.delete(transaction);

        assertEquals(1, deleted);
        assertNull(mDao.loadTransactionSync(transaction.getId()));
    }

    @Test
    public void deletePocket() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);

        int deleted = mDb.pocketDao().delete(pocket);

        assertEquals(1, deleted);
        assertNull(mDao.loadTransactionSync(transaction.getId()));
    }

    @Test
    public void deleteUser() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);

        int deleted = mDb.userDao().delete(user);

        assertEquals(1, deleted);
        assertNull(mDao.loadTransactionSync(transaction.getId()));
    }

    @Test
    public void update() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);

        transaction.setComment("Test update");
        int updated = mDao.update(transaction);

        assertEquals(1, updated);
        assertEquals(transaction, mDao.loadTransactionSync(transaction.getId()));
    }

    @Test
    public void insert() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);

        assertEquals(transaction, mDao.loadTransactionSync(transaction.getId()));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void insertWihtoutPocket() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity pocket = PocketsGenerator.testPocket(user);
        TransactionEntity transaction = TransactionsGenerator.testTransaction(pocket);

        mDb.userDao().insert(user);
//        mDb.pocketDao().insert(pocket);
        mDao.insert(transaction);
    }

}