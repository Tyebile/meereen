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
import com.tyebile.meereen.commons.controller.message.ResponseMessage;
import com.tyebile.meereen.commons.service.api.CreateEntityService;
import com.tyebile.meereen.commons.service.api.InsertService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tyebile.meereen.commons.controller.message.ResponseMessage.ok;

/**
 * 通用新增控制器<br>
 * 使用:实现该接口,注解@RestController 以及@RequestMapping("/myController")
 * 客户端调用: 通过POST请求,contentType为application/json 。参数为E泛型的json格式
 * <pre>
 * curl -l -H "Content-type: application/json" -X POST -d '{"field1":"value1","field2":"value2"}' http://domain/contextPath/myController
 * </pre>
 *
 * @author zhouhao
 * @since 3.0
 */
public interface CreateController<E, PK, M> {

    @Authorize(ignore = true)
    <S extends InsertService<E, PK> & CreateEntityService<E>> S getService();

    @Authorize(action = Permission.ACTION_ADD)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增")
    default ResponseMessage<PK> add(@RequestBody M data) {
        E entity = getService().createEntity();
        return ok(getService().insert(modelToEntity(data, entity)));
    }

    @Authorize(ignore = true)
    E modelToEntity(M model, E entity);
}
