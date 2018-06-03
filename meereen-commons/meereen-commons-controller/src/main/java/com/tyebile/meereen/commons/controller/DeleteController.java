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
import com.tyebile.meereen.commons.service.api.DeleteService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.tyebile.meereen.commons.controller.message.ResponseMessage.ok;

/**
 * 通用删除控制器
 *
 * @author zhouhao
 */
public interface DeleteController<PK> {

    @Authorize(ignore = true)
    DeleteService<PK> getService();

    @Authorize(action = Permission.ACTION_DELETE)
    @DeleteMapping(path = "/{id:.+}")
    @ApiOperation("删除数据")
    default ResponseMessage deleteByPrimaryKey(@PathVariable PK id) {
        return ok(getService().deleteByPk(id));
    }

}
