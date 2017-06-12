package com.moysa.saveup.data.response;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import java.util.concurrent.Executor;

/**
 * Created by Sergey Moysa
 */

public abstract class ExecutableLiveDataResponse<T> implements LiveDataResponse<T> {

    private static final String LOADING_ERROR = "Loading error";

    private final MutableLiveData<Response<T>> result = new MutableLiveData<>();
    private final Executor executor;

    @MainThread
    protected ExecutableLiveDataResponse(@NonNull Executor executor) {
        this.executor = executor;
    }

    @WorkerThread
    @Nullable
    protected abstract Response<T> manipulate();

    @Override
    @MainThread
    public final LiveData<Response<T>> executeAsLiveData() {

        result.setValue(Response.loading(null));

        executor.execute(() -> {

            Response<T> data = manipulate();

            if (data == null) {
                result.setValue(Response.error(LOADING_ERROR));
            } else {
                result.setValue(data);
            }
        });

        return result;
    }
}
