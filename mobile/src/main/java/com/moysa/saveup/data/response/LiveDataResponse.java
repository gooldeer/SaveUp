package com.moysa.saveup.data.response;

import android.arch.lifecycle.LiveData;

/**
 * Created by Sergey Moysa
 */

interface LiveDataResponse<T> {

    LiveData<Response<T>> executeAsLiveData();
}
