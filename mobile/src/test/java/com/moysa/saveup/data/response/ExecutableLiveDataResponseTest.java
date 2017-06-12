package com.moysa.saveup.data.response;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.moysa.saveup.utils.InstantExecutorFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.StubberImpl;

/**
 * Created by Sergey Moysa
 */
public class ExecutableLiveDataResponseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private ExecutableLiveDataResponse mResponse;
    private StubberImpl mStub;

    @Before
    public void setUp() throws Exception {

        mStub = Mockito.mock(StubberImpl.class);

        mResponse = new ExecutableLiveDataResponse(InstantExecutorFactory.getInstantExecutor()) {
            @Nullable
            @Override
            protected Response manipulate() {
                mStub.doNothing();
                return Response.success(mStub);
            }
        };
    }

    @Test
    public void manipulate() throws Exception {
        mResponse.executeAsLiveData();
        Mockito.verify(mStub).doNothing();
    }

    @Test
    public void executeAsLiveDataSuccess() throws Exception {
        LiveData<Response<String>> response = new ExecutableLiveDataResponse<String>(
                InstantExecutorFactory.getInstantExecutor()
        ) {

            @Nullable
            @Override
            protected Response<String> manipulate() {
                return Response.success("Success!");
            }
        }.executeAsLiveData();

        Assert.assertNotNull(response.getValue());
        Assert.assertEquals(Response.Status.SUCCESS, response.getValue().getStatus());
    }

    @Test
    public void executeAsLiveDataError() throws Exception {
        LiveData<Response<String>> response = new ExecutableLiveDataResponse<String>(
                InstantExecutorFactory.getInstantExecutor()
        ) {

            @Nullable
            @Override
            protected Response<String> manipulate() {
                return Response.error("Error!");
            }
        }.executeAsLiveData();

        Assert.assertNotNull(response.getValue());
        Assert.assertEquals(Response.Status.ERROR, response.getValue().getStatus());
    }

}