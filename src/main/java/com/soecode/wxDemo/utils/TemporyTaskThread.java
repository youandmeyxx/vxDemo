package com.soecode.wxDemo.utils;


import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TemporyTaskThread implements Runnable {
    private CountDownLatch callerCountDownLatch;
    private Object[] callerTranferdBusinessObjects;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public TemporyTaskThread(CountDownLatch callerCountDownLatch, Object[] callerTranferdBusinessObjects) {
        this.callerCountDownLatch = callerCountDownLatch;
        this.callerTranferdBusinessObjects = callerTranferdBusinessObjects;
    }

    public abstract void doBusinessInThread(Object[] var1) throws Exception;

    @Override
    public void run() {
        try {
            this.doBusinessInThread(this.callerTranferdBusinessObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.callerCountDownLatch.countDown();
    }

    protected <T> T getBusinessObject(int index) {
        return (T) this.callerTranferdBusinessObjects[index];
    }
}
