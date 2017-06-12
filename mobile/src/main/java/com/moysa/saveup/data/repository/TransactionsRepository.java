package com.moysa.saveup.data.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moysa.saveup.data.dao.TransactionDao;
import com.moysa.saveup.data.entity.TransactionEntity;
import com.moysa.saveup.data.response.ExecutableLiveDataResponse;
import com.moysa.saveup.data.response.LoadingLiveDataResponse;
import com.moysa.saveup.data.response.Response;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by sergey on 07.06.17.
 */

public class TransactionsRepository {

    @NonNull
    private final TransactionDao transactionDao;

    @NonNull
    private final Executor executor;

    public TransactionsRepository(@NonNull TransactionDao transactionDao,
                                  @NonNull Executor executor) {
        this.transactionDao = transactionDao;
        this.executor = executor;
    }

    public LiveData<Response<List<TransactionEntity>>> transactions(int pocketId) {

        return new LoadingLiveDataResponse<List<TransactionEntity>>() {
            @NonNull
            @Override
            protected LiveData<List<TransactionEntity>> loadFromDb() {
                return transactionDao.loadTransactions(pocketId);
            }
        }.executeAsLiveData();
    }

    public LiveData<Response<TransactionEntity>> transaction(int transactionId) {

        return new LoadingLiveDataResponse<TransactionEntity>() {
            @NonNull
            @Override
            protected LiveData<TransactionEntity> loadFromDb() {
                return transactionDao.loadTransaction(transactionId);
            }
        }.executeAsLiveData();
    }

    public LiveData<Response<TransactionEntity>> create(TransactionEntity transaction) {

        return new ExecutableLiveDataResponse<TransactionEntity>(executor) {
            @Nullable
            @Override
            protected Response<TransactionEntity> manipulate() {
                transactionDao.insert(transaction);
                return Response.success(transaction);
            }
        }.executeAsLiveData();
    }

    public LiveData<Response<TransactionEntity>> update(TransactionEntity transaction) {

        return new ExecutableLiveDataResponse<TransactionEntity>(executor) {
            @Nullable
            @Override
            protected Response<TransactionEntity> manipulate() {
                transactionDao.update(transaction);
                return Response.success(transaction);
            }
        }.executeAsLiveData();
    }

    public LiveData<Response<TransactionEntity[]>> delete(TransactionEntity... transactions) {

        return new ExecutableLiveDataResponse<TransactionEntity[]>(executor) {
            @Nullable
            @Override
            protected Response<TransactionEntity[]> manipulate() {
                transactionDao.delete(transactions);
                return Response.success(transactions);
            }
        }.executeAsLiveData();
    }
}
