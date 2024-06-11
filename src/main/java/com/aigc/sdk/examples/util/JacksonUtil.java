package com.aigc.sdk.examples.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class JacksonUtil {


    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        // mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        // 如果存在未知属性，则忽略不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许key没有双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许key有单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许整数以0开头
        mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        // 允许字符串中存在回车换行控制符
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 不转化value为null的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    public static String toJSONString(Object obj) {
        return obj != null ? toJSONString(obj, () -> "") : "";
    }

    public static String toJSONString(Object obj, Supplier<String> defaultSupplier) {
        try {
            return obj != null ? mapper.writeValueAsString(obj) : defaultSupplier.get();
        } catch (Exception e) {
            log.error("JsonUtil toJSONString 异常", e);
            return defaultSupplier.get();
        }
    }

    public static <T> T parseToObject(String value, Class<T> tClass) {
        return StringUtils.isNotBlank(value) ? parseToObject(value, tClass, () -> null) : null;
    }

    public static <T> T parseToObject(String value, Class<T> tClass, Supplier<T> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            return mapper.readValue(value, tClass);
        } catch (Exception e) {
            log.error("JsonUtil parseToObject 异常", e);
        }
        return defaultSupplier.get();
    }

    public static <T> List<T> parseToObjectList(String value, Class<T> tClass) {
        return StringUtils.isNotBlank(value) ? parseToObjectList(value, tClass, () -> null) : null;
    }

    public static <T> List<T> parseToObjectList(Object obj, Class<T> tClass) {
        return obj != null ? parseToObjectList(toJSONString(obj), tClass, () -> null) : null;
    }

    public static <T> List<T> parseToObjectList(String value, Class<T> tClass, Supplier<List<T>> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            return JSON.parseArray(value, tClass);
        } catch (Exception e) {
            log.error("JsonUtil parseToObjectList 异常", e);
        }
        return defaultSupplier.get();
    }

}
