package com.moysa.saveup.view.users;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.moysa.saveup.data.dao.UserDao;
import com.moysa.saveup.data.repository.UsersRepository;

import java.util.concurrent.Executors;

/**
 * Created by Sergey Moysa
 */

public class UsersViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final UsersRepository mRepo;

    public UsersViewModelProviderFactory(@NonNull UserDao userDao) {
        mRepo = new UsersRepository(userDao, Executors.newSingleThreadExecutor());
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.equals(UsersViewModel.class)) {
            //noinspection unchecked
            return (T) new UsersViewModel(mRepo);
        }

        return super.create(modelClass);
    }
}
