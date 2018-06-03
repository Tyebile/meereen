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

package com.tyebile.meereen.starter.spring.boot.init;

import java.util.List;
import java.util.Map;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
public interface DependencyUpgrader {
    DependencyUpgrader filter(List<Map<String, Object>> versions);

    void upgrade(UpgradeCallBack context);

}
