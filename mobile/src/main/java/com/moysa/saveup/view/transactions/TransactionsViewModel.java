package com.moysa.saveup.view.transactions;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.repository.TransactionsRepository;
import com.moysa.saveup.data.response.Response;

import java.util.List;

/**
 * Created by Sergey Moysa
 */

public class TransactionsViewModel extends ViewModel {

    @NonNull
    private final TransactionsRepository mRepo;

    public TransactionsViewModel(@NonNull TransactionsRepository mRepo) {
        this.mRepo = mRepo;
    }

    @MainThread
    @NonNull
    public LiveData<Response<List<TransactionEntity>>> getTransactions(int pocketId) {
        return mRepo.transactions(pocketId);
    }

    @MainThread
    @NonNull
    public LiveData<Response<TransactionEntity>> getTransaction(int transactionId) {
        return mRepo.transaction(transactionId);
    }

    @MainThread
    @NonNull
    public LiveData<Response<TransactionEntity>> create(@NonNull TransactionEntity transaction) {
        return mRepo.create(transaction);
    }

    @MainThread
    @NonNull
    public LiveData<Response<TransactionEntity>> update(@NonNull TransactionEntity transaction) {
        return mRepo.update(transaction);
    }

    @MainThread
    @NonNull
    public LiveData<Response<TransactionEntity[]>> delete(@NonNull TransactionEntity... transactions) {
        return mRepo.delete(transactions);
    }
}
