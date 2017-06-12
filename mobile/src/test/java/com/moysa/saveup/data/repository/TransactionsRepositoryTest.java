package com.moysa.saveup.data.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.moysa.saveup.data.dao.TransactionDao;
import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.utils.InstantExecutorFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Sergey Moysa
 */
public class TransactionsRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private TransactionDao mDao;
    private TransactionsRepository mRepo;

    @Before
    public void setUp() throws Exception {

        mDao = Mockito.mock(TransactionDao.class);
        mRepo = new TransactionsRepository(mDao, InstantExecutorFactory.getInstantExecutor());
    }

    @Test
    public void transactions() throws Exception {
        mRepo.transactions(1);
        Mockito.verify(mDao).loadTransactions(1);
    }

    @Test
    public void transaction() throws Exception {
        mRepo.transaction(1);
        Mockito.verify(mDao).loadTransaction(1);
    }

    @Test
    public void create() throws Exception {
        TransactionEntity stub = new TransactionEntity();
        stub.setComment("Test");

        mRepo.create(stub);
        Mockito.verify(mDao).insert(stub);
    }

    @Test
    public void update() throws Exception {
        TransactionEntity stub = new TransactionEntity();
        stub.setComment("Test");

        mRepo.update(stub);
        Mockito.verify(mDao).update(stub);
    }

    @Test
    public void delete() throws Exception {
        TransactionEntity stub = new TransactionEntity();
        stub.setComment("Test");

        mRepo.delete(stub);
        Mockito.verify(mDao).delete(stub);
    }

}