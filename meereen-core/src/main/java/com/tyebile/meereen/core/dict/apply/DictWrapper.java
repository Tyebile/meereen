package com.tyebile.meereen.core.dict.apply;

import com.tyebile.meereen.core.dict.DictDefineRepository;

/**
 * @author zhouhao
 * @since 3.0
 */
public interface DictWrapper {
    DictWrapper empty = (bean, repository) -> {};

    void wrap(Object bean, DictDefineRepository repository);


}
