package org.konghao.shiro.test;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class TestShiro {
	
	
	
	@Test
	public void testHelloworld(){
		
		
		//1、获取 SecurityManager 工厂，此处使用 Ini 配置文件初始化 SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory =
		new IniSecurityManagerFactory("classpath:shiro.ini");
		//2、得到 SecurityManager 实例 并绑定给 SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3、得到 Subject 及创建用户名/密码身份验证 Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		//UsernamePasswordToken token = new UsernamePasswordToken("zhang", "121");
		UsernamePasswordToken token = new UsernamePasswordToken("lisi", "123");
		try {
		//4、登录，即身份验证
		subject.login(token);
		System.out.println("验证通过");
		} catch (AuthenticationException e) {
		//5、身份验证失败
			System.out.println("验证失败");
		}
		System.out.println(  "用户是否登入:"+subject.isAuthenticated()  );  //断言用户已经登录
		//6、退出
		subject.logout();
		
		
	}
	
	
	private Subject login(String username,String password) {
		SecurityManager manager = new IniSecurityManagerFactory("classpath:shiro.ini").getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			System.out.println( "验证通过的个数:"+subject.getPrincipals().asList().size());
			System.out.println( "验证通过realm:"+subject.getPrincipals().asList());
			System.out.println("验证的过的realM数组:"+subject.getPrincipals().getRealmNames());
			System.out.println("验证通过:"+subject.getPrincipal());
			return subject;
		} catch (UnknownAccountException e) {
			System.out.println("用户名不存在!");
		} catch (IncorrectCredentialsException e) {
			System.out.println("用户密码不存在!");
		}
		
		return null;
	}
	private Subject login(String username,String password,String path) {
		SecurityManager manager = new IniSecurityManagerFactory(path).getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			System.out.println( "验证通过的个数:"+subject.getPrincipals().asList().size());
			System.out.println( "验证通过realm:"+subject.getPrincipals().asList());
			System.out.println("验证的过的realM数组:"+subject.getPrincipals().getRealmNames());
			System.out.println("验证通过:"+subject.getPrincipal());
			return subject;
		} catch (UnknownAccountException e) {
			System.out.println("用户名不存在!");
		} catch (IncorrectCredentialsException e) {
			System.out.println("用户密码不存在!");
		}
		
		return null;
	}

	@Test
	public void testBase() {
		SecurityManager manager = new IniSecurityManagerFactory("classpath:shiro.ini").getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("kh", "123");
		try {
			subject.login(token);  //进入子定义的验证!
			System.out.println( "验证通过的个数:"+subject.getPrincipals().asList().size());
			System.out.println( "验证通过realm:"+subject.getPrincipals().asList());
			System.out.println("验证的过的realM数组:"+subject.getPrincipals().getRealmNames());
			System.out.println("验证通过:"+subject.getPrincipal());
		} catch (UnknownAccountException e) {
			System.out.println("用户名不存在!----msg="+e.getMessage());
		} catch (IncorrectCredentialsException e) {
			System.out.println("用户密码不存在!msg="+e.getMessage());
		}
		
		
		System.out.println("end");
		
		
		
	}
	
	@Test
	public void testRole() {
		SecurityManager manager = new IniSecurityManagerFactory("classpath:shiro-role.ini").getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("kh", "123");
		try {
			subject.login(token);  //进入子定义的验证!
			System.out.println( "验证通过的个数:"+subject.getPrincipals().asList().size());
			System.out.println( "验证通过realm:"+subject.getPrincipals().asList());
			System.out.println("验证的过的realM数组:"+subject.getPrincipals().getRealmNames());
			System.out.println("验证通过:"+subject.getPrincipal());
		} catch (UnknownAccountException e) {
			System.out.println("用户名不存在!----msg="+e.getMessage());
		} catch (IncorrectCredentialsException e) {
			System.out.println("用户密码不存在!msg="+e.getMessage());
		}
		System.out.println("验证通过___>x现在开始验证角色");
		System.out.println(subject.hasRole("r1"));
		System.out.println(subject.hasAllRoles(Arrays.asList("r1","r2","r3")));
		System.out.println(subject.hasRoles(Arrays.asList("r1","r2","r3"))[1]);
		
		subject.checkRole("r3");  //如果没有这个角色会抛出异常!
		
		System.out.println("end 角色验证结束");
	}
	
	@Test
	public void testPermission1() {
		
		//shiro-permission.ini;
		Subject subject = login("kh","123","classpath:shiro-permission.ini");
		
		System.out.println(subject.isPermitted("user:delete"));
		System.out.println(subject.isPermitted("topic:create"));
		System.out.println(subject.isPermitted("dep:delete"));
		System.out.println(subject.isPermitted("classroom:create"));
	}
	
	@Test
	public void testPermission2() {
		Subject subject = login("lisi","123","classpath:shiro-permission.ini");
		System.out.println(subject.isPermitted("admin:user:delete:1"));
		System.out.println(subject.isPermitted("user:delete"));
		System.out.println(subject.isPermitted("test:user:view"));
	}
	
	@Test
	public void testMyPermission() {
	
		
		Subject subject = login("kh","123","classpath:shiro-permission.ini");
		System.out.println("解析授权");
		/**
		 * 1.先用resolver选择器解析 +user+delete
		 * 2.在调用StaticRealm. doGet获取授权
		 * 3. 使用resolver选择解析对addString进行解析,没有这不用操作
		 * 4.调用MyPermission.implies的方法,传入+user+delete 和存入的全部permission进行匹配
		 * 有的返回true,没有则返回false;
		 * 5.他们会循环匹配,知道找到true,不然返回false
		 */
		System.out.println(subject.isPermitted("+user+delete"));
		
		System.out.println(subject.isPermitted("+topic+create"));
//		System.out.println(subject.isPermitted("+topic+delete+1"));
//		System.out.println(subject.isPermitted("test:add"));
		System.out.println("最后一个是权限 在角色上面,这个权限是因为角色的原因添加上去的!");
		System.out.println(subject.isPermitted("classroom:add"));
	}
}
