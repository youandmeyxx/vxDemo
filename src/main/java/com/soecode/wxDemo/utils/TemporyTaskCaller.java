package com.soecode.wxDemo.utils;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang3.ArrayUtils;

public abstract class TemporyTaskCaller {
    private Object[] callerTranferdBusinessObjects;

    public TemporyTaskCaller() {
    }

    public abstract void createThreadToExecutionTask(int var1, ExecutorService var2, CountDownLatch var3, Object[] var4) throws Exception;

    public void call(int threadNum, Object... callerTranferdBusinessObjects) throws Exception {
        this.callerTranferdBusinessObjects = callerTranferdBusinessObjects;
        CountDownLatch currentMainThreadLatch = new CountDownLatch(threadNum);
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        this.createThreadToExecutionTask(threadNum, executor, currentMainThreadLatch, callerTranferdBusinessObjects);
        currentMainThreadLatch.await();
        executor.shutdown();
    }

    protected Object[] appendToEndOfCallerTranferdBusinessObjects(Object object) {
        return ArrayUtils.add(this.callerTranferdBusinessObjects, object);
    }

    protected <T> T getBusinessObject(int index) {
        return (T) this.callerTranferdBusinessObjects[index];
    }
}
