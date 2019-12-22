package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.daomin.Menu;
import com.itheima.daomin.Permission;
import com.itheima.daomin.Role;
import com.itheima.daomin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username); //查询用户基本信息不包含用户的巨额色
        if (user == null) {
            return null;
        }
        // 根据用户id 查询角色id
        Integer userid = user.getId();
        Set<Role> roles = roleDao.listroles(userid);
        for (Role role : roles) {
            Integer id = role.getId();
            Set<Permission> permissions = permissionDao.lisePermission(id);
            role.setPermissions(permissions);
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    public List<Menu> getMenu(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }
        // 根据用户id 查询角色id
        Integer userid = user.getId();
        Set<Role> roles = roleDao.listroles(userid);
        // 跟句角色id查询用户的菜单
        List<Menu> menuList = null;
        for (Role role : roles) {
            Integer id = role.getId();
            menuList = menuDao.getListMenu(id);

        }
        return menuList;
    }
}
