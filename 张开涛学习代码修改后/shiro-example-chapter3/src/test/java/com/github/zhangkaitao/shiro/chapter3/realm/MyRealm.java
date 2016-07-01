package com.github.zhangkaitao.shiro.chapter3.realm;

import com.github.zhangkaitao.shiro.chapter3.permission.BitPermission;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-26
 * <p>Version: 1.0
 */
public class MyRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        System.out.println("doGetAuthorizationInfo开始授权");
        System.out.println("添加角色role1");
        authorizationInfo.addRole("role1");
        System.out.println("添加角色role1 结束");
        
        
        System.out.println("添加角色role2");
        authorizationInfo.addRole("role2");
        System.out.println("添加角色role2 结束");
 
        authorizationInfo.addRole("role2");
        
        authorizationInfo.addObjectPermission(new BitPermission("+user1+10"));
        authorizationInfo.addObjectPermission(new WildcardPermission("user1:*"));
        authorizationInfo.addStringPermission("+user2+10"); //这种会使用权限调度转换成permission
        authorizationInfo.addStringPermission("user2:*");//这种会使用权限调度转换成permission
        												 //然后对角色进行分析,添加permission
        System.out.println("doGetAuthorizationInfo授权介素");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
      System.out.println("doGetAuthenticationInfo开始认证");
    	String username = (String)token.getPrincipal();  //得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码
        if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        System.out.println("doGetAuthenticationInfo认证结束");
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
