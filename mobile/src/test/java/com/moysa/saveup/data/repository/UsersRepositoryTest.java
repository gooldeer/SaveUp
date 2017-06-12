package com.moysa.saveup.data.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.moysa.saveup.data.AppDatabase;
import com.moysa.saveup.data.dao.UserDao;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.utils.InstantExecutorFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Sergey Moysa
 */
public class UsersRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private UsersRepository mRepo;
    private UserDao mDao;

    @Before
    public void setUp() throws Exception {

        mDao = Mockito.mock(UserDao.class);
        AppDatabase db = Mockito.mock(AppDatabase.class);
        Mockito.when(db.userDao()).thenReturn(mDao);

        mRepo = new UsersRepository(mDao, InstantExecutorFactory.getInstantExecutor());
    }

    @Test
    public void users() throws Exception {
        mRepo.users();
        Mockito.verify(mDao).loadAllUsers();
    }

    @Test
    public void user() throws Exception {
        mRepo.user(1);
        Mockito.verify(mDao).loadUser(1);
    }

    @Test
    public void create() throws Exception {

        UserEntity userEntity = new UserEntity();
        userEntity.setName("Test");

        mRepo.create(userEntity);
        Mockito.verify(mDao).insert(userEntity);
    }

    @Test
    public void update() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Test");

        mRepo.update(userEntity);
        Mockito.verify(mDao).update(userEntity);
    }

    @Test
    public void delete() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Test");

        mRepo.delete(userEntity);
        Mockito.verify(mDao).delete(userEntity);
    }

}