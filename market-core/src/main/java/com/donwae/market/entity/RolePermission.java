package com.donwae.market.entity;

import java.util.Date;

public class RolePermission extends RolePermissionKey {
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}