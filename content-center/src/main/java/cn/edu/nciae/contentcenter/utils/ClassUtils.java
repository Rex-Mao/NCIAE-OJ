package cn.edu.nciae.contentcenter.utils;

import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/8 1:03 PM
 */
public class ClassUtils {
    public static <S, T> T getSuperObjectFromSubObject(@NotNull S src, Class<T> superClassName) {
        try {
            T target = superClassName.getConstructor().newInstance();
            BeanUtils.copyProperties(src, target);
            return target;
        } catch (Exception e) {
            throw new ClassCastException("Convert subclass object to the superclass object failed.\n" + e.getMessage());
        }
    }
}
