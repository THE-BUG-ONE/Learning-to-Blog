package com.framework.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils(){}

    public static <V> V copyBean(Object source, Class<V> clazz) {
        V v;
        try {
            v = clazz.getConstructor().newInstance();
            BeanUtils.copyProperties(source, v);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage());
        }
        return v;
    }

    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
