package com.donwae.market.service.impl;

import com.donwae.market.dao.RoleMapper;
import com.donwae.market.entity.Role;
import com.donwae.market.entity.User;
import com.donwae.market.entity.UserRole;
import com.donwae.market.service.RoleService;
import com.donwae.market.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 * 2018/11/09 11:07:05
 * @author Jeremy Zhang
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUser(User user) {
        List<UserRole> userRoles = userRoleService.getUserRolesByUser(user);

        List<Role> roles = new ArrayList<>();
        for(UserRole userRole : userRoles) {
            Role role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
            if (role != null)
                roles.add(role);
        }

        return roles;
    }
}
