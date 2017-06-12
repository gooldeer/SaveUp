package com.moysa.saveup.data.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.moysa.saveup.data.dao.PocketDao;
import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.utils.InstantExecutorFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Sergey Moysa
 */
public class PocketsRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private PocketsRepository mRepo;
    private PocketDao mDao;

    @Before
    public void setUp() throws Exception {

        mDao = Mockito.mock(PocketDao.class);
        mRepo = new PocketsRepository(mDao, InstantExecutorFactory.getInstantExecutor());
    }

    @Test
    public void pockets() throws Exception {
        mRepo.pockets(1);
        Mockito.verify(mDao).loadPockets(1);
    }

    @Test
    public void pocket() throws Exception {
        mRepo.pocket(1);
        Mockito.verify(mDao).loadPocket(1);
    }

    @Test
    public void create() throws Exception {
        PocketEntity stub = new PocketEntity();
        stub.setName("Test");

        mRepo.create(stub);
        Mockito.verify(mDao).insert(stub);
    }

    @Test
    public void update() throws Exception {
        PocketEntity stub = new PocketEntity();
        stub.setName("Test");

        mRepo.update(stub);
        Mockito.verify(mDao).update(stub);
    }

    @Test
    public void delete() throws Exception {
        PocketEntity stub = new PocketEntity();
        stub.setName("Test");

        mRepo.delete(stub);
        Mockito.verify(mDao).delete(stub);
    }

}