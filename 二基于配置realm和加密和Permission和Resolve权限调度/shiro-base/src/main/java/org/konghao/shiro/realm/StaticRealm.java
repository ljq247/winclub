package org.konghao.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.konghao.shiro.permission.MyPermission;

public class StaticRealm extends AuthorizingRealm {
	//org.konghao.shiro.realm.StaticRealm
	/**
	 * 用来判断授权的
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole("r1");
		info.addRole("r2");
		System.out.println("开始添加授权");
		info.addStringPermission("+user+*"); //直接添加  权限
		info.addStringPermission("user:create");  //虽然在此处添加,但是以后还是会调用MyPermissionResolver 进行调度解析
		
		info.addObjectPermission(new MyPermission("+topic+create"));//自定义授权
		info.addObjectPermission(new MyPermission("+topic+delete+1"));//
		
		info.addObjectPermission(new WildcardPermission("test:*"));// 系统提供的授权解析
		System.out.println("结束开始添加授权:");
		return info;
	}

	/**
	 * 用来判断认证的
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();
		String password = new String((char[])token.getCredentials());
		if(!"kh".equals(username)) throw new UnknownAccountException("用户名不存在!");
		if(!"123".equals(password)) throw new IncorrectCredentialsException("密码错误");
		return new SimpleAuthenticationInfo("ynkonghao@gmail.com", password, getName()+"_自定义");
	}

}
