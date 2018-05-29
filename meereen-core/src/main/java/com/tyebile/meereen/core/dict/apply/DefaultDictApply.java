package com.tyebile.meereen.core.dict.apply;

import com.tyebile.meereen.core.dict.DictDefineRepository;
import com.tyebile.meereen.core.dict.EnumDict;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhouhao
 * @since 3.0
 */
public class DefaultDictApply implements DictApply {

    private DictDefineRepository repository;

    protected Map<Class, DictWrapper> cache = new ConcurrentHashMap<>();

    @Override
    public <T> T apply(T bean) {
        cache.computeIfAbsent(ClassUtils.getUserClass(bean.getClass()), this::createCache)
                .wrap(bean, repository);
        return bean;
    }

    protected DictWrapper createCache(Class bean) {
        StringBuilder method = new StringBuilder()
                .append("public void wrap(Object bean, com.tyebile.meereen.core.dict.DictDefineRepository repository)")
                .append("{\n")
                .append(bean.getName()).append(" target=(").append(bean.getName()).append(")bean;\n");

        ReflectionUtils.doWithFields(bean, field -> {
            Class type = field.getType();
            if (type.isArray()) {
                type = type.getComponentType();
            }
            //枚举字典并且枚举数量大于64
            if (type.isEnum() && EnumDict.class.isAssignableFrom(type) && type.getEnumConstants().length >= 64) {

            }
        });
        method.append("\n}");
        return DictWrapper.empty;
    }

}
