package com.moysa.saveup.manage;

import android.support.annotation.NonNull;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.model.Pocket;
import com.moysa.saveup.data.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Moysa
 */

public class UsersPocketsManager {

    @SuppressWarnings("NullableProblems")
    @NonNull
    private PocketManager mGeneralPocketManager;

    @NonNull
    private List<PocketManager> mSavingPocketManagers;

    public UsersPocketsManager(@NonNull PocketEntity generalPocket, @NonNull List<PocketEntity> savingPockets) {
        mSavingPocketManagers = new ArrayList<>();

        setGeneralPocket(generalPocket);
        setSavingPockets(savingPockets);
    }

    private void setGeneralPocket(@NonNull PocketEntity pocket) {

        pocket.setSavePercent(1f);
        mGeneralPocketManager = new PocketManager(pocket);
    }

    private void setSavingPockets(@NonNull List<PocketEntity> pockets) {

        pockets.forEach(pocket -> mSavingPocketManagers.add(new PocketManager(pocket)));
    }

    public List<Transaction> addTransaction(@NonNull Transaction transaction) {

        List<Transaction> result = new ArrayList<>();

        result.add(mGeneralPocketManager.workWith(transaction));

        mSavingPocketManagers.forEach(
                pocketManager -> {
                    Transaction t = pocketManager.workWith(transaction);
                    result.add(t);

                    TransactionEntity forGen = new TransactionEntity(t);
                    forGen.setPocketId(mGeneralPocketManager.getPocketId());
                    forGen.setAmount(0 - forGen.getAmount());

                    result.add(mGeneralPocketManager.workWith(forGen));
                });

        return result;
    }

    public Transaction addTransactionToPocket(@NonNull Transaction transaction, @NonNull Pocket pocket) {

        if (pocket.getId() == mGeneralPocketManager.getPocketId()) {

            return mGeneralPocketManager.workWith(transaction);
        } else {

            for (PocketManager pocketManager : mSavingPocketManagers) {
                if (pocket.getId() == pocketManager.getPocketId()) {
                    return pocketManager.workWith(transaction);
                }
            }
        }
        throw new IllegalArgumentException("Wrong pocket");
    }

    public Transaction addTransactionToGeneralPocket(@NonNull Transaction transaction) {
        return addTransactionToPocket(transaction, mGeneralPocketManager.getPocket());
    }

    @NonNull
    List<PocketManager> getSavingPocketManagers() {
        return mSavingPocketManagers;
    }
}
