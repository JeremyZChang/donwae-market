package com.donwae.market.config;

import com.donwae.market.entity.Permission;
import com.donwae.market.entity.Role;
import com.donwae.market.entity.User;
import com.donwae.market.service.PermissionService;
import com.donwae.market.service.RoleService;
import com.donwae.market.service.UserRoleService;
import com.donwae.market.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * TODO
 *
 * @auther Jeremy
 * 2018/11/8 下午3:28
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token.getPrincipal() == null) {
            return null;
        }
        // 获取用户名和密码。
        String name = token.getPrincipal().toString();
        String password = new String((char[]) token.getCredentials());

        User user = userService.findByName(name);

        if (user == null || !password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("登录失败，用户名或密码错误");
        }

        //验证authenticationToken和simpleAuthenticationInfo的信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, password, getName());

        // 返回一个身份信息
        return simpleAuthenticationInfo;
    }

    /**
     * 角色权限和对应权限添加
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        // 获取用户对象
        User user = userService.findByName(name);
        // 添加角色和权限

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<Role> roles = roleService.getRolesByUser(user);

        for (Role role : roles) {
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getName());

            // 添加权限
            List<Permission> permissions = permissionService.getPermissionByRole(role);
            for (Permission permission : permissions) {
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }
        return simpleAuthorizationInfo;
    }

}
