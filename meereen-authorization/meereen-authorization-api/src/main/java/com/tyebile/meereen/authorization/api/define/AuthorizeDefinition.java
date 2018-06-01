package com.tyebile.meereen.authorization.api.define;

import com.tyebile.meereen.authorization.api.Permission;
import com.tyebile.meereen.authorization.api.Role;
import com.tyebile.meereen.authorization.api.User;
import com.tyebile.meereen.authorization.api.annotation.Logical;

import java.util.Set;

/**
 * 权限控制定义,定义权限控制的方式
 *
 * @author zhouhao
 * @since 3.0
 */
public interface AuthorizeDefinition {

    /**
     * @return 验证时机
     */
    Phased getPhased();

    /**
     * 优先级,如果获取到多个权限控制定义是,则先判断优先级高的
     *
     * @return 优先级
     */
    int getPriority();

    /**
     * @return 是否进行数据权限控制
     * @see com.tyebile.meereen.authorization.api.access.DataAccessController
     */
    boolean isDataAccessControl();

    /**
     * @return 要控制的权限
     */
    Set<String> getPermissions();

    String[] getPermissionDescription();

    String[] getActionDescription();

    /**
     * 要控制的权限事件,仅当{@link this#getPermissions()}不为空的时候生效
     *
     * @return 权限事件
     * @see Permission#getActions()
     */
    Set<String> getActions();

    /**
     * 控制角色访问
     *
     * @return 要控制的角色id集合
     * @see Role#getId()
     * @see com.tyebile.meereen.authorization.api.Authentication#hasRole(String)
     */
    Set<String> getRoles();

    /**
     * 控制用户访问
     *
     * @return 要控制的用户id集合
     * @see User#getId()
     */
    Set<String> getUser();

    /**
     * 使用脚本进行控制
     *
     * @return 脚本
     */
    Script getScript();

    /**
     * @return 当无权限时, 返回的消息
     */
    String getMessage();

    /**
     * @return 当存在多个配置, 如:配置了多个permission或者actions. 进行判断的逻辑(或者,并且)
     */
    Logical getLogical();

    boolean isEmpty();

    DataAccessDefinition getDataAccessDefinition();
}
