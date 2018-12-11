package com.donwae.market.service;

import com.donwae.market.entity.Role;
import com.donwae.market.entity.User;

import java.util.List;

/**
 * 服务类
 * 2018/11/09 11:08:13
 * @author Jeremy Zhang
 */
public interface RoleService {
    /**
     * //TODO
     * 2018/11/13 15:32:09
     * @author Jeremy Zhang
     */
    public List<Role> getRolesByUser(User user);
}
