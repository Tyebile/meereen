/*
 * Copyright 2016 http://www.hswebframework.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.tyebile.meereen.commons.controller;


import io.swagger.annotations.ApiOperation;
import com.tyebile.meereen.authorization.api.Permission;
import com.tyebile.meereen.authorization.api.annotation.Authorize;
import com.tyebile.meereen.authorization.api.annotation.Logical;
import com.tyebile.meereen.commons.controller.message.ResponseMessage;
import com.tyebile.meereen.commons.service.api.CreateEntityService;
import com.tyebile.meereen.commons.service.api.UpdateService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 通用更新控制器
 *
 * @author zhouhao
 */
public interface UpdateController<E, PK, M> {
    <S extends UpdateService<E, PK> & CreateEntityService<E>> S getService();

    @Authorize(action = Permission.ACTION_UPDATE)
    @PutMapping(path = "/{id}")
    @ApiOperation("修改数据")
    default ResponseMessage<Integer> updateByPrimaryKey(@PathVariable PK id, @RequestBody M data) {
        E entity = getService().createEntity();
        return ResponseMessage.ok(getService().updateByPk(id, modelToEntity(data, entity)));
    }

    @Authorize(action = {Permission.ACTION_UPDATE, Permission.ACTION_ADD}, logical = Logical.AND)
    @PatchMapping
    @ApiOperation("新增或者修改")
    default ResponseMessage<PK> saveOrUpdate(@RequestBody M data) {
        E entity = getService().createEntity();
        return ResponseMessage.ok(getService().saveOrUpdate(modelToEntity(data, entity)));
    }

    /**
     * 将model转为entity
     *
     * @param model
     * @param entity
     * @return 转换后的结果
     * @see org.hswebframework.web.commons.model.Model
     * @see com.tyebile.meereen.commons.entity.Entity
     */
    @Authorize(ignore = true)
    E modelToEntity(M model, E entity);
}
