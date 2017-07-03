package com.moysa.saveup.manage;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.model.Transaction;
import com.moysa.saveup.data.model.User;
import com.moysa.saveup.utils.PocketsGenerator;
import com.moysa.saveup.utils.UsersGenerator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sergey Moysa
 */
public class UsersPocketsManagerTest {

    private static final float SAVE_PERCENT = 0.1f;
    private UsersPocketsManager mManager;

    private PocketEntity mGeneralPocket;
    private List<PocketEntity> mPockets;

    @Before
    public void setUp() throws Exception {
        User user = UsersGenerator.testUser();
        mManager = new UsersPocketsManager();

        mPockets = PocketsGenerator.testPockets(user);
        mPockets.forEach(pocketEntity -> pocketEntity.setSavePercent(SAVE_PERCENT));

        mGeneralPocket = PocketsGenerator.testPocket(user);
        mGeneralPocket.setId(210);
        mGeneralPocket.setSavePercent(1f);
        mGeneralPocket.setName(UsersPocketsManager.GENERAL_PREFIX + user.getId() * 31);

        mPockets.add(mGeneralPocket);

        mManager.instantiateWith(user, mPockets);
    }

    @Test
    public void instantiateWith() throws Exception {

    }

    @Test
    public void addTransaction() throws Exception {

        float oldAmount = mGeneralPocket.getAmount();
        float value = 100f;

        final float[] savedSum = {0, 0};
        mManager.getSavingPocketManagers().forEach(manager -> savedSum[0] += manager.getAmount());

        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transaction.getAmount()).thenReturn(value);

        List<Transaction> result = mManager.addTransaction(transaction);

        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertEquals(oldAmount + value, mGeneralPocket.getAmount(), 0);

        mManager.getSavingPocketManagers().forEach(manager -> savedSum[1] += manager.getAmount());

        assertEquals(savedSum[0] + value * SAVE_PERCENT * (mPockets.size() - 1), savedSum[1], 0);
    }

    @Test
    public void addTransactionToPocket() throws Exception {
        PocketEntity pocket = mPockets.get(0);
        float value = 100f;

        float oldAm = pocket.getAmount();

        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transaction.getAmount()).thenReturn(value);
        Mockito.when(transaction.getPocketId()).thenReturn(pocket.getId());

        mManager.addTransactionToPocket(transaction, pocket);

        assertEquals(oldAm + value, pocket.getAmount(), 0);
    }

    @Test
    public void addTransactionToGeneralPocket() throws Exception {
        float value = 100f;
        float oldAm = mGeneralPocket.getAmount();

        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transaction.getAmount()).thenReturn(value);
        Mockito.when(transaction.getPocketId()).thenReturn(mGeneralPocket.getId());

        mManager.addTransactionToGeneralPocket(transaction);

        assertEquals(oldAm + value, mGeneralPocket.getAmount(), 0);
    }

}