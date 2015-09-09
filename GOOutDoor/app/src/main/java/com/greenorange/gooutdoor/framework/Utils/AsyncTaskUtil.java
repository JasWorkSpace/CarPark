package com.greenorange.gooutdoor.framework.Utils;

import android.os.AsyncTask;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * Helper class to execute an AsyncTask in parallel if SDK version is 11 or newer.
 */
public class AsyncTaskUtil {
    private static Method sMethodExecuteOnExecutor;
    private static Executor sExecutor;
    static {
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                sExecutor = (Executor) AsyncTask.class.getField("THREAD_POOL_EXECUTOR")
                        .get(null);
                sMethodExecuteOnExecutor = AsyncTask.class.getMethod(
                        "executeOnExecutor", Executor.class, Object[].class);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <Param> void executeInParallel(AsyncTask<Param, ?, ?> task, Param... params) {
        if (Build.VERSION.SDK_INT < 11) {
            task.execute(params);
        } else {
            try {
                sMethodExecuteOnExecutor.invoke(task, sExecutor, params);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private AsyncTaskUtil() {
    }
}

