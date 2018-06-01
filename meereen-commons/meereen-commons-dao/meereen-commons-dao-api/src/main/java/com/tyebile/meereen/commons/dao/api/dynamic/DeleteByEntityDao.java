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

package com.tyebile.meereen.commons.dao.api.dynamic;

import com.tyebile.meereen.commons.entity.Entity;

/**
 * 根据实体类条件进行删除,删除条件根据实体类进行解析。解析方式和{@link QueryByEntityDao#query}一致
 *
 * @author zhouhao
 * @since 3.0
 */
public interface DeleteByEntityDao {
    int delete(Entity entity);
}
