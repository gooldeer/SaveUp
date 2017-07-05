package com.moysa.saveup.view.transactions;

import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.repository.TransactionsRepository;
import com.moysa.saveup.utils.PocketsGenerator;
import com.moysa.saveup.utils.TransactionsGenerator;
import com.moysa.saveup.utils.UsersGenerator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Sergey Moysa
 */
public class TransactionsViewModelTest {

    private TransactionsViewModel mViewModel;
    private TransactionsRepository mRepo;

    @Before
    public void setUp() throws Exception {
        mRepo = Mockito.mock(TransactionsRepository.class);
        mViewModel = new TransactionsViewModel(mRepo);
    }

    @Test
    public void getTransactions() throws Exception {
        mViewModel.getTransactions(0);

        Mockito.verify(mRepo).transactions(0);
    }

    @Test
    public void getTransaction() throws Exception {
        mViewModel.getTransaction(0);

        Mockito.verify(mRepo).transaction(0);
    }

    @Test
    public void create() throws Exception {
        TransactionEntity transaction = generateTransaction();

        mViewModel.create(transaction);

        Mockito.verify(mRepo).create(transaction);
    }

    private TransactionEntity generateTransaction() {
        return TransactionsGenerator.testTransaction(
                PocketsGenerator.testPocket(
                        UsersGenerator.testUser()));
    }

    @Test
    public void update() throws Exception {
        TransactionEntity transaction = generateTransaction();

        mViewModel.update(transaction);

        Mockito.verify(mRepo).update(transaction);
    }

    @Test
    public void delete() throws Exception {
        TransactionEntity transaction = generateTransaction();

        mViewModel.delete(transaction);

        Mockito.verify(mRepo).delete(transaction);
    }

}