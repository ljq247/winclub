package com.github.zhangkaitao.shiro.chapter6.realm;

import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import junit.framework.Assert;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Test;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class UserRealmTest extends BaseTest {
    

    @Test
      public void testLoginSuccess() {
    	// 密码验证 ,会调用realm.下的authentication 进行验证!
        login("classpath:shiro.ini", u1.getUsername(), password);
        System.out.println("1验证通过!");
        Assert.assertTrue(subject().isAuthenticated());
        
        //如果不调用,则不会验证
        System.out.println("2验证通过!");
    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithUnknownUsername() {
        login("classpath:shiro.ini", u1.getUsername() + "1", password);
    }

    @Test(expected = IncorrectCredentialsException.class)  //抛出密码错误!
    public void testLoginFailWithErrorPassowrd() {
        login("classpath:shiro.ini", u1.getUsername(), password + "1"); //
    }

    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithLocked() {
        login("classpath:shiro.ini", u4.getUsername(), password + "1");  //抛出账号锁定
    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithLimitRetryCount() {
        for(int i = 1; i <= 5; i++) {
            try {
                login("classpath:shiro.ini", u3.getUsername(), password + "1");
                System.out.println("验证通过i=:"+i);
            } catch (Exception e) {/*ignore*/
            	
            	System.out.println("抛出异常!:"+i);
            }
        }
        login("classpath:shiro.ini", u3.getUsername(), password + "1");

        //需要清空缓存，否则后续的执行就会遇到问题(或者使用一个全新账户测试)
    }


    @Test
    public void testHasRole() {
        login("classpath:shiro.ini", u1.getUsername(), password );
        
        Assert.assertTrue(subject().hasRole("admin"));  //验证角色  有
        
    }

    @Test
    public void testNoRole() {
        login("classpath:shiro.ini", u2.getUsername(), password);
        Assert.assertFalse(subject().hasRole("admin"));//咩有
    }

    @Test
    public void testHasPermission() {
        login("classpath:shiro.ini", u1.getUsername(), password);
        Assert.assertTrue(subject().isPermittedAll("user:create", "menu:create"));//验证权限
    }

    @Test
    public void testNoPermission() {
        login("classpath:shiro.ini", u2.getUsername(), password);
        Assert.assertFalse(subject().isPermitted("user:create"));
    }

}
