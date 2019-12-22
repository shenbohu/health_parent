package com.itheima.daomin;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable {
private Integer[] menuIds;
private Integer[] permissionIds;
private Role role;

    @Override
    public String toString() {
        return "Request{" +
                "menuIds=" + Arrays.toString(menuIds) +
                ", permissionIds=" + Arrays.toString(permissionIds) +
                ", role=" + role +
                '}';
    }

    public Integer[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Integer[] menuIds) {
        this.menuIds = menuIds;
    }

    public Integer[] getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Integer[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Request(Integer[] menuIds, Integer[] permissionIds, Role role) {
        this.menuIds = menuIds;
        this.permissionIds = permissionIds;
        this.role = role;
    }

    public Request() {
    }
}
