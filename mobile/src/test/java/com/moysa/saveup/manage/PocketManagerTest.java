package com.moysa.saveup.manage;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.model.Transaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Sergey Moysa
 */
public class PocketManagerTest {

    private static final int POCKET_ID = 1;
    private static final float POCKET_AMOUNT = 1000f;
    private static final float POCKET_SAVE_PERCENT = 0.1f;

    private PocketManager mManager;
    private PocketEntity mPocket;

    @Before
    public void setUp() throws Exception {
        mPocket = new PocketEntity();

        mPocket.setId(POCKET_ID);
        mPocket.setAmount(POCKET_AMOUNT);
        mPocket.setSavePercent(POCKET_SAVE_PERCENT);

        mManager = new PocketManager(mPocket);
    }

    @Test
    public void proceedTransaction() throws Exception {

        Transaction t = Mockito.mock(Transaction.class);

        Mockito.when(t.getAmount()).thenReturn(80f);

        mManager.proceedTransaction(t);

        assertEquals(mManager.getAmount(), POCKET_AMOUNT + 80f, 0);
    }

    @Test
    public void makeTransaction() throws Exception {

        Transaction t = Mockito.mock(Transaction.class);
        float amount = 80f;

        Mockito.when(t.getAmount()).thenReturn(amount);

        Transaction result = mManager.makeTransaction(t);

        assertEquals(amount * POCKET_SAVE_PERCENT, result.getAmount(), 0);

    }

    @Test
    public void testWorkWithTransactionWithinPocket() throws Exception {

        Transaction t = Mockito.mock(Transaction.class);
        float amount = 80f;

        Mockito.when(t.getAmount()).thenReturn(amount);
        Mockito.when(t.getPocketId()).thenReturn(POCKET_ID);

        assertEquals(t, mManager.workWith(t));
        assertEquals(POCKET_AMOUNT + amount, mManager.getAmount(), 0);
    }

    @Test
    public void testWorkWithTransactionFromGeneralPocket() throws Exception {

        Transaction t = Mockito.mock(Transaction.class);
        float amount = 80f;

        Mockito.when(t.getAmount()).thenReturn(amount);
        Mockito.when(t.getPocketId()).thenReturn(POCKET_ID + 1);

        Transaction actual = mManager.workWith(t);

        assertNotEquals(t, actual);
        assertEquals(amount * POCKET_SAVE_PERCENT, actual.getAmount(), 0);
        assertEquals(POCKET_AMOUNT + amount * POCKET_SAVE_PERCENT, mManager.getAmount(), 0);
    }
}