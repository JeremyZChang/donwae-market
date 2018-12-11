package com.donwae.market.service;

import com.donwae.market.entity.User;
import com.donwae.market.entity.UserRole;

import java.util.List;

/**
 * 服务类
 * 2018/11/09 11:08:26
 * @author Jeremy Zhang
 */
public interface UserRoleService {

    /**
     * //TODO
     * 2018/11/13 14:56:36
     * @author Jeremy Zhang
     */
    public List<UserRole> getUserRolesByUser(User user);
    /**
     * //TODO
     * 2018/11/13 15:13:07
     * @author Jeremy Zhang
     */
    public List<UserRole> getUserRolesByUserId(String userId);
}
