package com.moysa.saveup.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.moysa.saveup.data.AppDatabase;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.utils.LiveDataTestUtil;
import com.moysa.saveup.utils.UsersGenerator;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sergey Moysa
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private AppDatabase mDb;
    private UserDao mDao;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.userDao();
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }

    @Test
    public void loadAllUsers() throws Exception {
        List<UserEntity> expected = UsersGenerator.testUsers();
        mDao.insertAll(expected);

        LiveData<List<UserEntity>> data = mDao.loadAllUsers();
        List<UserEntity> users = LiveDataTestUtil.getValue(data);

        assertThat(users,
                IsIterableContainingInOrder.contains(
                        expected.toArray(new UserEntity[expected.size()])
                )
        );
    }

    @Test
    public void loadAllUsersSync() throws Exception {
        List<UserEntity> expected = UsersGenerator.testUsers();
        mDao.insertAll(expected);

        List<UserEntity> users = mDao.loadAllUsersSync();

        assertThat(users,
                IsIterableContainingInOrder.contains(
                        expected.toArray(new UserEntity[expected.size()])
                )
        );
    }

    @Test
    public void insertAll() throws Exception {
        List<UserEntity> expected = UsersGenerator.testUsers();
        mDao.insertAll(expected);

        List<UserEntity> users = mDao.loadAllUsersSync();

        assertThat(users,
                IsIterableContainingInOrder.contains(
                        expected.toArray(new UserEntity[expected.size()])
                )
        );
    }

    @Test
    public void insert() throws Exception {
        UserEntity expected = UsersGenerator.testUser();
        mDao.insert(expected);

        assertEquals(expected, mDao.loadUserSync(expected.getId()));
    }

    @Test
    public void loadUser() throws Exception {
        UserEntity expected = UsersGenerator.testUser();
        mDao.insert(expected);

        LiveData<UserEntity> data = mDao.loadUser(expected.getId());

        assertEquals(expected, LiveDataTestUtil.getValue(data));
    }

    @Test
    public void loadUserSync() throws Exception {
        UserEntity expected = UsersGenerator.testUser();
        mDao.insert(expected);

        assertEquals(expected, mDao.loadUserSync(expected.getId()));
    }

    @Test
    public void delete() throws Exception {
        UserEntity user = UsersGenerator.testUser();
        mDao.insert(user);

        int deleted = mDao.delete(user);

        assertEquals(1, deleted);
        assertTrue(mDao.loadAllUsersSync().size() == 0);
    }

    @Test
    public void update() throws Exception {
        UserEntity expected = UsersGenerator.testUser();
        mDao.insert(expected);

        expected.setName("Test update");
        mDao.update(expected);

        assertEquals(expected, mDao.loadUserSync(expected.getId()));
    }

}