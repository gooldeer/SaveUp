package com.moysa.saveup.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.moysa.saveup.data.AppDatabase;
import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.utils.LiveDataTestUtil;
import com.moysa.saveup.utils.PocketsGenerator;
import com.moysa.saveup.utils.UsersGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sergey Moysa
 */
@RunWith(AndroidJUnit4.class)
public class PocketDaoTest {

    private AppDatabase mDb;
    private PocketDao mDao;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.pocketDao();
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }

    @Test
    public void loadPockets() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        List<PocketEntity> expected = PocketsGenerator.testPockets(user);

        mDb.userDao().insert(user);
        mDao.insertAll(expected);

        LiveData<List<PocketEntity>> data = mDao.loadPockets(user.getId());

        assertEquals(expected, LiveDataTestUtil.getValue(data));
    }

    @Test
    public void loadPocketsSync() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        List<PocketEntity> expected = PocketsGenerator.testPockets(user);

        mDb.userDao().insert(user);
        mDao.insertAll(expected);

        assertEquals(expected, mDao.loadPocketsSync(user.getId()));
    }

    @Test
    public void insertAll() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        List<PocketEntity> expected = PocketsGenerator.testPockets(user);

        mDb.userDao().insert(user);
        mDao.insertAll(expected);

        assertEquals(expected, mDao.loadPocketsSync(user.getId()));
    }

    @Test
    public void loadPocket() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity expected = PocketsGenerator.testPocket(user);

        mDb.userDao().insert(user);
        mDao.insert(expected);

        LiveData<PocketEntity> data = mDao.loadPocket(expected.getId());

        assertEquals(expected, LiveDataTestUtil.getValue(data));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void loadPocketWithoutUserInserted() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity expected = PocketsGenerator.testPocket(user);

//        mDb.userDao().insert(user);
        mDao.insert(expected);
    }

    @Test
    public void loadPocketSync() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity expected = PocketsGenerator.testPocket(user);

        mDb.userDao().insert(user);
        mDao.insert(expected);

        assertEquals(expected, mDao.loadPocketSync(expected.getId()));
    }

    @Test
    public void delete() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity expected = PocketsGenerator.testPocket(user);

        mDb.userDao().insert(user);
        mDao.insert(expected);

        int deleted = mDao.delete(expected);
        assertEquals(1, deleted);
        assertTrue(mDao.loadPocketsSync(user.getId()).size() == 0);
    }

    @Test
    public void deleteUser() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity expected = PocketsGenerator.testPocket(user);

        mDb.userDao().insert(user);
        mDao.insert(expected);

        mDb.userDao().delete(user);
        assertTrue(mDao.loadPocketsSync(user.getId()).size() == 0);
    }

    @Test
    public void update() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity expected = PocketsGenerator.testPocket(user);

        mDb.userDao().insert(user);
        mDao.insert(expected);

        expected.setName("Test update");
        int updated = mDao.update(expected);

        assertEquals(1, updated);
        assertEquals(expected, mDao.loadPocketSync(expected.getId()));
    }

    @Test
    public void insert() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        PocketEntity expected = PocketsGenerator.testPocket(user);

        mDb.userDao().insert(user);
        mDao.insert(expected);

        assertEquals(expected, mDao.loadPocketSync(expected.getId()));
    }

}