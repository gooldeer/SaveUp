package com.moysa.saveup.data.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moysa.saveup.data.dao.PocketDao;
import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.response.ExecutableLiveDataResponse;
import com.moysa.saveup.data.response.LoadingLiveDataResponse;
import com.moysa.saveup.data.response.Response;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Sergey Moysa
 */

public class PocketsRepository {

    @NonNull
    private final PocketDao pocketDao;

    @NonNull
    private final Executor executor;

    public PocketsRepository(@NonNull PocketDao pocketDao, @NonNull Executor executor) {
        this.pocketDao = pocketDao;
        this.executor = executor;
    }

    @NonNull
    public LiveData<Response<List<PocketEntity>>> pockets(int userId) {

        return new LoadingLiveDataResponse<List<PocketEntity>>() {
            @NonNull
            @Override
            protected LiveData<List<PocketEntity>> loadFromDb() {
                return pocketDao.loadPockets(userId);
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<PocketEntity>> pocket(int pocketId) {

        return new LoadingLiveDataResponse<PocketEntity>() {
            @NonNull
            @Override
            protected LiveData<PocketEntity> loadFromDb() {
                return pocketDao.loadPocket(pocketId);
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<PocketEntity>> create(@NonNull PocketEntity pocket) {

        return new ExecutableLiveDataResponse<PocketEntity>(executor) {

            @Nullable
            @Override
            protected Response<PocketEntity> manipulate() {
                pocketDao.insert(pocket);
                return Response.success(pocket);
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<PocketEntity>> update(@NonNull PocketEntity pocket) {

        return new ExecutableLiveDataResponse<PocketEntity>(executor) {
            @Nullable
            @Override
            protected Response<PocketEntity> manipulate() {
                pocketDao.update(pocket);
                return Response.success(pocket);
            }
        }.executeAsLiveData();
    }

    @NonNull
    public LiveData<Response<PocketEntity[]>> delete(@NonNull PocketEntity... pockets) {

        return new ExecutableLiveDataResponse<PocketEntity[]>(executor) {
            @Nullable
            @Override
            protected Response<PocketEntity[]> manipulate() {
                pocketDao.delete(pockets);
                return Response.success(pockets);
            }
        }.executeAsLiveData();
    }
}
