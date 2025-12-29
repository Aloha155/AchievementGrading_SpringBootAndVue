package com.bank.performance.core.util;


import com.bank.performance.core.exception.ProgramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author yj
 * @date 2021/4/7 11:38
 */
public class ThreadUtil {

    private static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    private static final Integer DEFAULT_METHOD_AND_CLASS_NAME_NUM = 1;

    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static Long getThreadId() {
        return Thread.currentThread().getId();
    }


    /**
     * 默认是返回调用该方法的方法名，例：我在userServiceImpl的get方法调用这个，则返回get
     *
     * @return 默认返回上一级的方法
     */
    public static String getMethodName() {
        return getMethodName(DEFAULT_METHOD_AND_CLASS_NAME_NUM);
    }

    /**
     * 返回调用该方法的方法名或者上一级方法名，通过num判断
     *
     * @return 默认返回上一级的方法
     */
    public static String getMethodName(Integer num) {
        return getClassAndMethodName(num, true, false);
    }

    /**
     * 默认是返回调用该方法的方法名，例：我在userServiceImpl的get方法调用这个，则返回get
     *
     * @return 默认返回上一级的方法
     */
    public static String getClassName() {
        return getClassName(DEFAULT_METHOD_AND_CLASS_NAME_NUM);
    }

    /**
     * 返回调用该方法的方法名或者上一级方法名，通过num判断
     *
     * @return 默认返回上一级的方法
     */
    public static String getClassName(Integer num) {
        return getClassAndMethodName(num, true, true);
    }


    /**
     * 获取某一级的方法名,默认返回调用该方法的类型
     *
     * @param num 1为当前，2为上一级。。。。。。以及类推
     * @return 方法名
     */
    public static String getClassAndMethodName(Integer num, Boolean sign, Boolean flag) {
        //参数为空报错
        if (RegexUtil.checkObjectIsNull(num) || RegexUtil.checkObjectIsNull(sign)) {
            throw new ProgramException("参数不能为空");
        }
        num = (num < 0) ? Math.abs(num) : num;
        if (sign) {
            num = 3 + num;
        } else {
            if (num < 3) {
                num = 3;
            }
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        num = (num > stackTrace.length) ? stackTrace.length - 1 : num;
        StackTraceElement stackTraceElement = stackTrace[num];
        return (flag) ? stackTraceElement.getClassName() : stackTraceElement.getMethodName();
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("休眠异常");
        }
    }
}