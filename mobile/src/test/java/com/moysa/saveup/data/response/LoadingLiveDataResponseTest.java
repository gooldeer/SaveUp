package com.moysa.saveup.data.response;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Sergey Moysa
 */
public class LoadingLiveDataResponseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private LoadingLiveDataResponse<String> mResponse;
    private MutableLiveData<String> mData;

    @Before
    public void setUp() throws Exception {

        mData = new MutableLiveData<>();

        mResponse = new LoadingLiveDataResponse<String>() {
            @NonNull
            @Override
            protected LiveData<String> loadFromDb() {
                return mData;
            }
        };
    }

    @Test
    public void loadFromDb() throws Exception {

        assertEquals(mResponse.loadFromDb(), mData);
    }

    @Test
    public void executeAsLiveDataSuccess() throws Exception {

        LiveData<Response<String>> result = mResponse.executeAsLiveData();

        Observer<Response<String>> observer = r -> {
        };
        result.observeForever(observer);

        assertNotNull(result.getValue());
        assertEquals(Response.Status.LOADING, result.getValue().getStatus());

        String value = "Success!";
        mData.setValue(value);

        assertNotNull(result.getValue());
        assertEquals(Response.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(value, result.getValue().getData());

        result.removeObserver(observer);
    }

    @Test
    public void executeAsLiveDataError() throws Exception {

        LiveData<Response<String>> result = mResponse.executeAsLiveData();

        Observer<Response<String>> observer = r -> {
        };
        result.observeForever(observer);

        assertNotNull(result.getValue());
        assertEquals(Response.Status.LOADING, result.getValue().getStatus());

        mData.setValue(null);

        assertNotNull(result.getValue());
        assertEquals(Response.Status.ERROR, result.getValue().getStatus());
        assertNull(result.getValue().getData());

        result.removeObserver(observer);
    }
}