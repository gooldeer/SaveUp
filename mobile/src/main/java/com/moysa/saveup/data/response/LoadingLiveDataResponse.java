package com.moysa.saveup.data.response;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * Created by Sergey Moysa
 */

public abstract class LoadingLiveDataResponse<T> implements LiveDataResponse<T> {

    private static final String LOADING_ERROR = "Loading error";

    private final MediatorLiveData<Response<T>> result = new MediatorLiveData<>();

    @MainThread
    protected LoadingLiveDataResponse() {

    }

    // Called to get the cached data from the database
    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    @Override
    @MainThread
    public final LiveData<Response<T>> executeAsLiveData() {

        result.setValue(Response.loading(null));
        LiveData<T> dbSource = loadFromDb();

        result.addSource(
                dbSource,
                newData -> {
                    if (newData == null) {
                        result.setValue(Response.error(LOADING_ERROR));
                    } else {
                        result.setValue(Response.success(newData));
                    }
                }
        );

        return result;
    }
}
