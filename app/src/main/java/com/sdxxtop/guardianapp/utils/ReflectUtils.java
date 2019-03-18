package com.sdxxtop.guardianapp.utils;

import android.view.View;

import java.lang.reflect.Field;
import java.util.List;

public class ReflectUtils {

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static List<View> getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (List<View>) field.get(object);
        } catch (Exception e) {

            return null;
        }
    }

}
