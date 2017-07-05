package com.moysa.saveup.view.pockets;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.moysa.saveup.data.entity.PocketEntity;
import com.moysa.saveup.data.repository.PocketsRepository;
import com.moysa.saveup.data.response.Response;

import java.util.List;

/**
 * Created by Sergey Moysa
 */

public class PocketsViewModel extends ViewModel {

    @NonNull
    private final PocketsRepository mRepo;

    PocketsViewModel(@NonNull PocketsRepository mRepo) {
        this.mRepo = mRepo;
    }

    @MainThread
    @NonNull
    public LiveData<Response<List<PocketEntity>>> getPockets(int userId) {
        return mRepo.pockets(userId);
    }

    @MainThread
    @NonNull
    public LiveData<Response<PocketEntity>> getPocket(int pocketId) {
        return mRepo.pocket(pocketId);
    }

    @MainThread
    @NonNull
    public LiveData<Response<PocketEntity>> create(@NonNull PocketEntity pocket) {
        return mRepo.create(pocket);
    }

    @MainThread
    @NonNull
    public LiveData<Response<PocketEntity>> update(@NonNull PocketEntity pocket) {
        return mRepo.update(pocket);
    }

    @MainThread
    @NonNull
    public LiveData<Response<PocketEntity[]>> delete(@NonNull PocketEntity... pockets) {
        return mRepo.delete(pockets);
    }
}
