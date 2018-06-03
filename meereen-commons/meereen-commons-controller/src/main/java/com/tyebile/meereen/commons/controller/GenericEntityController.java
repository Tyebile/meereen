/*
 *
 *  * Copyright 2016 http://www.hswebframework.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.tyebile.meereen.commons.controller;

import com.tyebile.meereen.authorization.api.annotation.Authorize;
import com.tyebile.meereen.commons.entity.Entity;
import com.tyebile.meereen.commons.entity.GenericEntity;
import com.tyebile.meereen.commons.service.api.CrudService;

/**
 * 通用实体的增删改查控制器
 *
 * @author zhouhao
 * @see GenericEntity
 * @see CrudController
 * @see CrudService
 */
public interface GenericEntityController<E extends GenericEntity<PK>, PK, Q extends Entity, M>
        extends CrudController<E, PK, Q, M> {

    @Override
    @Authorize(ignore = true)
    CrudService<E, PK> getService();

}
