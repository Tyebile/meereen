package com.tyebile.meereen.core.dict.defaults;

import lombok.extern.slf4j.Slf4j;
import com.tyebile.meereen.core.dict.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhouhao
 * @since 3.0
 */
@Slf4j
public class DefaultDictDefineRepository implements DictDefineRepository {
    protected static final Map<String, DictDefine> parsedDict = new HashMap<>();

    public static void registerDefine(DictDefine define) {
        parsedDict.put(define.getId(), define);
    }

    public static <T extends Enum & EnumDict> ClassDictDefine parseEnumDict(Class<T> type) {
        log.debug("parse enum dict :{}", type);

        Dict dict = type.getAnnotation(Dict.class);

        DefaultClassDictDefine define = new DefaultClassDictDefine();
        define.setField("");
        if (dict != null) {
            define.setId(dict.id());
            define.setParserId(dict.parserId());
            define.setComments(dict.comments());
            define.setAlias(dict.alias());
        } else {
            define.setId(type.getSimpleName());
            define.setAlias(type.getName());
            define.setComments(type.getSimpleName());
        }

        List<ItemDefine> items = new ArrayList<>();

        for (T t : type.getEnumConstants()) {
            items.add(DefaultItemDefine.builder()
                    .text(t.getText())
                    .comments(t.getComments())
                    .value(String.valueOf(t.getValue()))
                    .build());

        }
        define.setItems(items);

        return define;

    }

    @Override
    public DictDefine getDefine(String id) {
        return parsedDict.get(id);
    }

    private List<Field> parseField(Class type) {
        if (type == Object.class) {
            return Collections.emptyList();
        }
        List<Field> fields = new ArrayList<>();
        ReflectionUtils.doWithFields(type, fields::add);
        return fields;
    }

    @Override
    public List<ClassDictDefine> getDefine(Class type) {
        return this.parseDefine(type);
    }

    protected List<ClassDictDefine> parseDefine(Class type) {
        List<ClassDictDefine> defines = new ArrayList<>();

        if (type.isEnum() && EnumDict.class.isAssignableFrom(type)) {
            return Arrays.asList(parseEnumDict(type));
        }
        for (Field field : parseField(type)) {
            Dict dict = field.getAnnotation(Dict.class);
            if (dict == null) {
                continue;
            }
            String id = dict.id();
            DictDefine dictDefine = getDefine(id);
            if (dictDefine instanceof ClassDictDefine) {
                defines.add(((ClassDictDefine) dictDefine));
            } else {
                DefaultClassDictDefine define;
                if (dictDefine != null) {
                    List<ItemDefine> items = dictDefine.getItems()
                            .stream()
                            .map(item -> DefaultItemDefine.builder()
                                    .text(item.getText())
                                    .value(item.getValue())
                                    .comments(String.join(",", item.getComments()))
                                    .build())
                            .collect(Collectors.toList());
                    define = DefaultClassDictDefine.builder()
                            .id(id)
                            .alias(dictDefine.getAlias())
                            .comments(dictDefine.getComments())
                            .field(field.getName())
                            .items(items)
                            .build();

                } else {
                    List<ItemDefine> items = Arrays
                            .stream(dict.items())
                            .map(item -> DefaultItemDefine.builder()
                                    .text(item.text())
                                    .value(item.value())
                                    .comments(String.join(",", item.comments()))
                                    .build()).collect(Collectors.toList());
                    define = DefaultClassDictDefine.builder()
                            .id(id)
                            .alias(dict.alias())
                            .comments(dict.comments())
                            .field(field.getName())
                            .items(items)
                            .build();
                }
                defines.add(define);
            }
        }
        return defines;
    }

    @Override
    public void addDefine(DictDefine dictDefine) {
        registerDefine(dictDefine);
    }
}
