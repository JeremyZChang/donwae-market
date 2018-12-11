package com.donwae.market.service.impl;

import com.donwae.market.dao.UserRoleMapper;
import com.donwae.market.entity.User;
import com.donwae.market.entity.UserRole;
import com.donwae.market.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 * 2018/11/09 11:05:55
 * @author Jeremy
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> getUserRolesByUser(User user) {
        if(user != null && StringUtils.isNotBlank(user.getId()))
            return this.getUserRolesByUserId(user.getId());
        else
            return null;
    }

    @Override
    public List<UserRole> getUserRolesByUserId(String userId) {
        return userRoleMapper.selectByUserId(userId);
    }
}
