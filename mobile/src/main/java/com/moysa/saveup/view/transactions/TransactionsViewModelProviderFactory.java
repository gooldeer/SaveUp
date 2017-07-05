package com.moysa.saveup.view.transactions;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.moysa.saveup.data.dao.TransactionDao;
import com.moysa.saveup.data.repository.TransactionsRepository;

import java.util.concurrent.Executors;

/**
 * Created by Sergey Moysa
 */

public class TransactionsViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final TransactionsRepository mRepo;

    public TransactionsViewModelProviderFactory(@NonNull TransactionDao dao) {
        mRepo = new TransactionsRepository(dao, Executors.newSingleThreadExecutor());
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.equals(TransactionsViewModel.class)) {
            //noinspection unchecked
            return (T) new TransactionsViewModel(mRepo);
        }

        return super.create(modelClass);
    }
}
