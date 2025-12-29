package com.bank.performance.core.util;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author yj
 * @date 2021/4/7 14:06
 */
public class RegexUtil {

    private Logger logger = LoggerFactory.getLogger(RegexUtil.class);


    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     * <pre>
     * RegexUtil.checkObjectIsNull(null)      = true
     * RegexUtil.checkObjectIsNull("")        = true
     * RegexUtil.checkObjectIsNull(" ")       = true
     * RegexUtil.checkObjectIsNull("bob")     = false
     * RegexUtil.checkObjectIsNull("  bob  ") = false
     * RegexUtil.checkObjectIsNull(1)         = false
     * RegexUtil.checkObjectIsNull(false)     = false
     * </pre>
     */
    public static boolean checkObjectIsNull(Object data) {
        if (data instanceof String) {
            return checkStringIsNull((String) data);
        }
        return data == null;
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     * <pre>
     * RegexUtil.checkStringIsNull(null)      = true
     * RegexUtil.checkStringIsNull("")        = true
     * RegexUtil.checkStringIsNull(" ")       = true
     * RegexUtil.checkStringIsNull("bob")     = false
     * RegexUtil.checkStringIsNull("  bob  ") = false
     * </pre>
     */
    public static boolean checkStringIsNull(String data) {
        return StringUtils.isBlank(data);
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     * <pre>
     * RegexUtil.checkObjectIsNotNull(null)      = false
     * RegexUtil.checkObjectIsNotNull("")        = false
     * RegexUtil.checkObjectIsNotNull(" ")       = false
     * RegexUtil.checkObjectIsNotNull(1)         = true
     * RegexUtil.checkObjectIsNotNull(false)     = true
     * </pre>
     */
    public static boolean checkObjectIsNotNull(Object data) {
        if (data instanceof String) {
            return checkStringIsNotNull((String) data);
        }
        return data != null;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     * <pre>
     * RegexUtil.checkStringIsNotNull(null)      = false
     * RegexUtil.checkStringIsNotNull("")        = false
     * RegexUtil.checkStringIsNotNull(" ")       = false
     * RegexUtil.checkStringIsNotNull("bob")     = true
     * RegexUtil.checkStringIsNotNull("  bob  ") = true
     * </pre>
     */
    public static boolean checkStringIsNotNull(String data) {
        return !StringUtils.isBlank(data);
    }

    public static boolean checkPhone(){
        return true;
    }

    public static boolean checkPassword(){
        return false;
    }


}