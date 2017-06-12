package com.moysa.saveup.data.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moysa.saveup.data.dao.UserDao;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.data.response.ExecutableLiveDataResponse;
import com.moysa.saveup.data.response.LoadingLiveDataResponse;
import com.moysa.saveup.data.response.Response;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Sergey Moysa
 */

public class UsersRepository {

    @NonNull
    private final UserDao userDao;
    @NonNull
    private final Executor executor;

    public UsersRepository(@NonNull UserDao userDao, @NonNull Executor executor) {
        this.userDao = userDao;
        this.executor = executor;
    }

    @NonNull
    public LiveData<Response<List<UserEntity>>> users() {

        return new LoadingLiveDataResponse<List<UserEntity>>() {

            @NonNull
            @Override
            protected LiveData<List<UserEntity>> loadFromDb() {
                return userDao.loadAllUsers();
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<UserEntity>> user(int id) {

        return new LoadingLiveDataResponse<UserEntity>() {
            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return userDao.loadUser(id);
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<UserEntity>> create(@NonNull UserEntity user) {

        return new ExecutableLiveDataResponse<UserEntity>(executor) {

            @Nullable
            @Override
            protected Response<UserEntity> manipulate() {
                userDao.insert(user);
                return Response.success(user);
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<UserEntity>> update(@NonNull UserEntity user) {

        return new ExecutableLiveDataResponse<UserEntity>(executor) {
            @Nullable
            @Override
            protected Response<UserEntity> manipulate() {
                userDao.update(user);
                return Response.success(user);
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<UserEntity[]>> delete(@NonNull UserEntity... users) {

        return new ExecutableLiveDataResponse<UserEntity[]>(executor) {
            @Nullable
            @Override
            protected Response<UserEntity[]> manipulate() {
                userDao.delete(users);
                return Response.success(users);
            }
        }.executeAsLiveData();
    }
}
