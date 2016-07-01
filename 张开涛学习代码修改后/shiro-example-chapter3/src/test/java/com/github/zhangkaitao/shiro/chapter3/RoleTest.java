package com.github.zhangkaitao.shiro.chapter3;

import junit.framework.Assert;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-26
 * <p>Version: 1.0
 */
public class RoleTest extends BaseTest {

    @Test
    public void testHasRole() {//1.
//    	zhang=123,role1,role2
//    			wang=123,role1
        Subject login = login("classpath:shiro-role.ini", "zhang", "123");
       
        // login.
        //判断拥有角色：role1
        Assert.assertTrue(subject().hasRole("role1"));
        //判断拥有角色：role1 and role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        Assert.assertEquals(false, result[2]);
        System.out.println("testHasRole--->end");
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {  //2.
    	
//    	zhang=123,role1,role2
//    			wang=123,role1
        login("classpath:shiro-role.ini", "zhang", "123");
        //断言拥有角色：role1
        subject().checkRole("role1");
        System.out.println("role1检查通过");
        //断言拥有角色：role1 and role3 失败抛出异常
        subject().checkRoles("role1", "role3"); 
        System.out.println("role1,role3检查通过");
    }

}
