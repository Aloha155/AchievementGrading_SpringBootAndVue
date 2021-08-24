package com.hsf.hsfbs.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hsf.hsfbs.core.exception.ProgramException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author meiyaSQL
 * @Date 2019/8/28
 */
public class JsonUtil {

    /**
     * 把json文件转成对象
     *
     * @param path json文件路径
     * @param type 转换的类格式
     */
    public static <T> T jsonFileToObject(String path, Class<T> type) {
        try {
            return JSONObject.parseObject(
                    IOUtils.toString(
                            new InputStreamReader(
                                    new ClassPathResource(path).getInputStream(),
                                    StandardCharsets.UTF_8)),
                    type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把json文件转成List
     *
     * @param path json文件路径
     * @param type 转换的类格式
     */
    public static <T> List<T> jsonFileToArray(String path, Class<T> type) {
        if (RegexUtil.checkObjectIsNull(path)) {
            throw new ProgramException("路径为空");
        }
        try {
            return JSONArray.parseArray(
                    IOUtils.toString(
                            new InputStreamReader(
                                    new ClassPathResource(path).getInputStream(),
                                    StandardCharsets.UTF_8)),
                    type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把json数据转成对象
     *
     * @param json json类型的数据
     * @param type 转换的类格式
     */
    public static <T> T jsonStringToObject(Object json, Class<T> type) {
        try {
            if (json instanceof String) {
                return JSONObject.parseObject((String) json, type);
            } else {
                return JSONObject.parseObject(JSON.toJSONString(json), type);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把json数据转成List
     *
     * @param json json类型的数据
     * @param type 转换的类格式
     */
    public static <T> List<T> jsonStringToArray(Object json, Class<T> type) {
        try {
            if (json instanceof String) {
                return JSONArray.parseArray((String) json, type);
            } else {
                return JSONArray.parseArray(JSON.toJSONString(json), type);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static<T> String objectToString(T t){
        return JSON.toJSONString(t);
    }
}

