package com.jksk.commonutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.*;


/**
 * FastJson 工具类
 *
 * @Author
 * @create 2019-07-03
 **/
public class FastJsonUtil {

    private static SerializeConfig mapping = new SerializeConfig();

    private static SimpleDateFormatSerializer formatSerializer = new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss");

    private static final SerializerFeature[] features;

    static {
        features = new SerializerFeature[] {
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullStringAsEmpty
                // WriteMapNullValue,//是否输出值为null的字段,默认为false
                // WriteNullStringAsEmpty,//字符类型字段如果为null,输出为"",而非null
                // WriteNullNumberAsZero,//数值字段如果为null,输出为0,而非null
                //
        };
    }

    public FastJsonUtil() {
    }

    public static <T> T parseToClass(String jsonStr, Class<?> clazz) {
        T javaObject = (T) JSON.toJavaObject(JSON.parseObject(jsonStr), clazz);
        return javaObject;
    }

    public static String parseToJSON(Object object) {
        return JSON.toJSONString(object, configMapping(), features[0]);
    }

    public static String parseUnicodeJSON(Object object) {
        return JSON.toJSONString(object, new SerializerFeature[]{SerializerFeature.BrowserCompatible});
    }

    public static String parseJSONString(Object object, SimplePropertyPreFilter filter) {
        return JSON.toJSONString(object, filter, features[0]);
    }

    public static String parseJSONString(Object object, String formatDate) {
        return JSON.toJSONString(object, configMapping(formatDate), new SerializerFeature[0]);
    }

    public static List<?> getListJSONToJava(String jsonString, Class<?> pojoClass) {
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        List<Object> list = new ArrayList<Object>();

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object pojoValue = JSON.toJavaObject(jsonObject, pojoClass);
            list.add(pojoValue);
        }

        return list;
    }

    public static Map<Object, Object> getMapJSON(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Map<Object, Object> parseJSONMap = new LinkedHashMap(jsonObject);
        return parseJSONMap;
    }

    private static SerializeConfig configMapping() {
        mapping.put(Date.class, formatSerializer);
        return mapping;
    }

    private static SerializeConfig configMapping(String format) {
        SerializeConfig mapping = new SerializeConfig();
        mapping.put(Date.class, new SimpleDateFormatSerializer(format));
        return mapping;
    }

    public static SimplePropertyPreFilter filterProperty(Class<?> className, String... param) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(className, param);
        return filter;
    }
}
