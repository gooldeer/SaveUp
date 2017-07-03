package com.moysa.saveup.manage;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.model.Pocket;
import com.moysa.saveup.data.model.Transaction;
import com.moysa.saveup.data.model.User;
import com.moysa.saveup.manage.exception.UsersPocketsManagerInstantiationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Moysa
 */

public class UsersPocketsManager {

    static final String GENERAL_PREFIX = "general";

    @NonNull
    private PocketManager mGeneralPocketManager;

    @NonNull
    private List<PocketManager> mSavingPocketManagers;

    public void instantiateWith(@NonNull User user, @NonNull List<PocketEntity> pockets) {

        String generalPocketName = GENERAL_PREFIX + (user.getId() * 31);

        mSavingPocketManagers = new ArrayList<>();

        pockets.forEach(pocket -> {

            if (!TextUtils.isEmpty(pocket.getName())) {

                if (pocket.getName().equals(generalPocketName)) {
                    mGeneralPocketManager = new PocketManager(pocket);
                } else {
                    PocketManager manager = new PocketManager(pocket);
                    mSavingPocketManagers.add(manager);
                }
            } else {
                throw new UsersPocketsManagerInstantiationException(
                        "Null pocket name. id=" + pocket.getId());
            }
        });
    }

    public List<Transaction> addTransaction(@NonNull Transaction transaction) {

        List<Transaction> result = new ArrayList<>();

        result.add(mGeneralPocketManager.workWith(transaction));

        mSavingPocketManagers.forEach(
                pocketManager -> result.add(pocketManager.workWith(transaction)));

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
