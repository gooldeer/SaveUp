package com.moysa.saveup.data.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.moysa.saveup.data.response.Response.Status.ERROR;
import static com.moysa.saveup.data.response.Response.Status.LOADING;
import static com.moysa.saveup.data.response.Response.Status.SUCCESS;

/**
 * Created by Sergey Moysa
 */

public class Response<T> {

    @Nullable
    private final T data;

    @Nullable
    private final String error;

    @NonNull
    private final Status status;

    private Response(@NonNull Status status, @Nullable T data, @Nullable String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Response<T> success(@NonNull T data) {
        return new Response<>(SUCCESS, data, null);
    }

    public static <T> Response<T> error(@NonNull String error) {
        return new Response<>(ERROR, null, error);
    }

    public static <T> Response<T> loading(@Nullable T data) {
        return new Response<>(LOADING, data, null);
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getError() {
        return error;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    public enum Status {
        SUCCESS, ERROR, LOADING
    }
}
