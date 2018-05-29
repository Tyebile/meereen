package com.tyebile.meereen.core.dict;

import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface DictDefineRepository {
    DictDefine getDefine(String id);

    List<ClassDictDefine> getDefine(Class type);

    void addDefine(DictDefine dictDefine);
}
