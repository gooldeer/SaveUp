package com.moysa.saveup.view.pockets;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.moysa.saveup.data.dao.PocketDao;
import com.moysa.saveup.data.repository.PocketsRepository;

import java.util.concurrent.Executors;

/**
 * Created by Sergey Moysa
 */

public class PocketsViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final PocketsRepository mRepo;

    public PocketsViewModelProviderFactory(@NonNull PocketDao pocketDao) {
        mRepo = new PocketsRepository(pocketDao, Executors.newSingleThreadExecutor());
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.equals(PocketsViewModel.class)) {
            //noinspection unchecked
            return (T) new PocketsViewModel(mRepo);
        }

        return super.create(modelClass);
    }
}
