package com.tyebile.meereen.authorization.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 用户授权信息,当前登录用户的权限信息,包括用户的基本信息,角色,权限集合等常用信息<br>
 * 获取方式:
 * <ul>
 * <li>springmvc 入参方式: ResponseMessage myTest(Authorization auth){}</li>
 * <li>静态方法方式:AuthorizationHolder.get();</li>
 * </ul>
 *
 * @see AuthenticationHolder
 * @see AuthenticationManager
 */
public interface Authentication extends Serializable {

    /**
     * 获取当前登录的用户权限信息
     * <pre>
     *
     *   Authentication auth= Authentication.current().get();
     *   //如果权限信息不存在将抛出{@link NoSuchElementException}建议使用下面的方式获取
     *   Authentication auth=Authentication.current().orElse(null);
     *   //或者
     *   Authentication auth=Authentication.current().orElseThrow(UnAuthorizedException::new);
     * </pre>
     *
     * @return 返回Optional对象进行操作
     * @see Optional
     * @see AuthenticationHolder
     */
    static Optional<Authentication> current() {
        return Optional.ofNullable(AuthenticationHolder.get());
    }

    /**
     * @return 用户信息
     */
    User getUser();

    /**
     * @return 用户持有的角色集合
     */
    List<Role> getRoles();

    /**
     * @return 用户持有的权限集合
     */
    List<Permission> getPermissions();

    /**
     * 根据id获取角色,角色不存在则返回null
     *
     * @param id 角色id
     * @return 角色信息
     */
    default Optional<Role> getRole(String id) {
        if (null == id) {
            return Optional.empty();
        }
        return getRoles().stream()
                .filter(role -> role.getId().equals(id))
                .findAny();
    }

    /**
     * 根据权限id获取权限信息,权限不存在则返回null
     *
     * @param id 权限id
     * @return 权限信息
     */
    default Optional<Permission> getPermission(String id) {
        if (null == id) {
            return Optional.empty();
        }
        return getPermissions().stream()
                .filter(permission -> permission.getId().equals(id))
                .findAny();
    }

    /**
     * 判断是否持有某权限以及对权限的可操作事件
     *
     * @param permissionId 权限id {@link Permission#getId()}
     * @param actions      可操作事件 {@link Permission#getActions()} 如果为空,则不判断action,只判断permissionId
     * @return 是否持有权限
     */
    default boolean hasPermission(String permissionId, String... actions) {
        return getPermission(permissionId)
                .filter(permission -> actions.length == 0 || permission.getActions().containsAll(Arrays.asList(actions)))
                .isPresent();
    }

    /**
     * @param roleId 角色id {@link Role#getId()}
     * @return 是否拥有某个角色
     */
    default boolean hasRole(String roleId) {
        return getRole(roleId).isPresent();
    }

}
