package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class ServiceTest extends BaseTest {


    @Test
    public void testUserRolePermissionRelation() {

        //zhang
        Set<String> roles = userService.findRoles(u1.getUsername());
        Assert.assertEquals(1, roles.size()); //张是管理员,
        Assert.assertTrue(roles.contains(r1.getRole()));  //判断集合roles中是否存在 r1管理员!

        Set<String> permissions = userService.findPermissions(u1.getUsername()); //管理员拥有[user:create, user:update, menu:create]
        Assert.assertEquals(3, permissions.size());
        Assert.assertTrue(permissions.contains(p3.getPermission()));

        //li
        roles = userService.findRoles(u2.getUsername());
        Assert.assertEquals(0, roles.size());
        permissions = userService.findPermissions(u2.getUsername());
        Assert.assertEquals(0, permissions.size());


        //解除 admin-menu:update关联
        permissions = userService.findPermissions(u1.getUsername());
        roleService.uncorrelationPermissions(r1.getId(), p3.getId());
        Assert.assertEquals(2, permissions.size());
        Assert.assertFalse(permissions.contains(p3.getPermission()));


        //删除一个permission
        permissionService.deletePermission(p2.getId());
        permissions = userService.findPermissions(u1.getUsername());
        Assert.assertEquals(1, permissions.size());

        //解除 zhang-admin关联
        userService.uncorrelationRoles(u1.getId(), r1.getId());
        roles = userService.findRoles(u1.getUsername());
        Assert.assertEquals(0, roles.size());


    }

}
