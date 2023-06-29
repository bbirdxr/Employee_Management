package com.example.employee.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MapObjectUtil {

    /**
     * 把 map 转为 object
     *
     * @param map map
     * @param cla cla
     * @param <T>    返回值类型
     * @return 返回值
     * @throws Exception newInstance 可能会抛出的异常
     */
    public static <T> T mapToObject(Map map, Class<T> cla) throws Exception {
        if (map == null) {
            return null;
        }
        T obj = cla.newInstance();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }

    /**
     * 将 map 转为 object
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value == null)
                value = "";
            map.put(keyName, value);
        }
        return map;
    }

}