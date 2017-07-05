package com.moysa.saveup.view.users;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.data.repository.UsersRepository;
import com.moysa.saveup.data.response.Response;

import java.util.List;

/**
 * Created by Sergey Moysa
 */

public class UsersViewModel extends ViewModel {

    @NonNull
    private final UsersRepository mRepo;

    UsersViewModel(@NonNull UsersRepository mRepo) {
        this.mRepo = mRepo;
    }

    @MainThread
    @NonNull
    public LiveData<Response<List<UserEntity>>> getUsersList() {
        return mRepo.users();
    }

    @MainThread
    @NonNull
    public LiveData<Response<UserEntity>> getUser(int id) {
        return mRepo.user(id);
    }

    @MainThread
    @NonNull
    public LiveData<Response<UserEntity>> create(@NonNull UserEntity user) {
        return mRepo.create(user);
    }

    @MainThread
    @NonNull
    public LiveData<Response<UserEntity>> update(@NonNull UserEntity user) {
        return mRepo.update(user);
    }

    @MainThread
    @NonNull
    public LiveData<Response<UserEntity[]>> delete(@NonNull UserEntity... users) {
        return mRepo.delete(users);
    }
}
