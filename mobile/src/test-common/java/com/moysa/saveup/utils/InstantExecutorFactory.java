package com.moysa.saveup.utils;

import java.util.concurrent.Executor;

/**
 * Created by Sergey Moysa
 */

public class InstantExecutorFactory {

    public static Executor getInstantExecutor() {
        return Runnable::run;
    }

}
