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

package com.tyebile.meereen.authorization.api.annotation;

import com.tyebile.meereen.authorization.api.Permission;
import com.tyebile.meereen.authorization.api.access.DataAccessConfig;
import com.tyebile.meereen.authorization.api.access.DataAccessController;
import com.tyebile.meereen.authorization.api.define.Phased;

import java.lang.annotation.*;

/**
 * 数据级权限控制注解,用于进行需要数据级别权限控制的声明.
 * <p>
 * 此注解仅用于声明此方法需要进行数据级权限控制,具体权限控制方式由控制器实{@link DataAccessController}现
 * </p>
 *
 * @author zhouhao
 * @see DataAccessController
 * @see Authorize#dataAccess()
 * @since 3.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresDataAccess {

    /**
     * @return permission id ,如果为空将继承 {@link Authorize#permission()}
     * @see Permission#getId()
     *
     */
    String permission() default "";

    /**
     * @return action array ,如果为空将继承 {@link Authorize#action()}
     * @see DataAccessConfig#getAction()
     */
    String[] action() default {};

    String[] supportType() default {};

    /**
     * @return logical
     */
    Logical logical() default Logical.AND;

    /**
     * @return 自定义控制器bean名称
     */
    String controllerBeanName() default "";

    /**
     * @return 自定义控制器类型
     */
    Class<DataAccessController> controllerClass() default DataAccessController.class;

    Phased phased() default Phased.before;

    /**
     * @return id参数名称
     */
    String idParamName() default "id";

    /**
     * @return 是否忽略, 忽略后将不进行权限控制
     */
    boolean ignore() default false;

}
