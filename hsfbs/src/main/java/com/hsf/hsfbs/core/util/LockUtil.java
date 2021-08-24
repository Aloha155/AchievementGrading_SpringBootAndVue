package com.hsf.hsfbs.core.util;


import com.hsf.hsfbs.core.exception.ProgramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author yj
 * @date 2021/4/1 15:32
 */
public class LockUtil {

    private Logger logger = LoggerFactory.getLogger(LockUtil.class);

    private static final Lock lock;

    static {
        lock = new ReentrantLock();
    }

    public static Lock getLock() {
        return lock;
    }

    public static void lock() {
        lock.lock();
    }

    public static void unLock() {
        lock.unlock();
    }

    public static boolean tryLock() {
        return lock.tryLock();
    }

    public static boolean tryLock(long time, TimeUnit timeUnit) {
        try {
            return lock.tryLock(time, timeUnit);
        } catch (InterruptedException e) {
            throw new ProgramException("上锁失败");
        }
    }


}