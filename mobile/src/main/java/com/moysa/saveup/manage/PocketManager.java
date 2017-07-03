package com.moysa.saveup.manage;

import android.support.annotation.NonNull;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.model.Transaction;

import java.util.Date;

/**
 * Created by Sergey Moysa
 */

public class PocketManager {

    @NonNull
    private PocketEntity mPocket;

    public PocketManager(@NonNull PocketEntity pocket) {
        this.mPocket = pocket;
    }

    public Transaction workWith(@NonNull Transaction general) {

        if (general.getPocketId() == mPocket.getId()) {

            proceedTransaction(general);

            return general;

        } else {

            Transaction withinPocket = makeTransaction(general);
            proceedTransaction(withinPocket);

            return withinPocket;
        }
    }

    protected void proceedTransaction(@NonNull Transaction transaction) {

        mPocket.setAmount(
                mPocket.getAmount() + transaction.getAmount()
        );
    }

    protected Transaction makeTransaction(@NonNull Transaction generalTransaction) {

        TransactionEntity result = new TransactionEntity();

        result.setPocketId(mPocket.getId());
        result.setDate(new Date());
        result.setAmount(generalTransaction.getAmount() * mPocket.getSavePercent());

        return result;
    }

    @NonNull
    PocketEntity getPocket() {
        return mPocket;
    }

    public int getPocketId() {
        return mPocket.getId();
    }

    public float getAmount() {
        return mPocket.getAmount();
    }
}
