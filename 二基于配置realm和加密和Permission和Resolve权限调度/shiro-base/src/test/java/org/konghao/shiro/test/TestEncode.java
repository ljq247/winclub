package org.konghao.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class TestEncode {
	
	@Test
	public void testEncode(){
		System.out.println(new Md5Hash("123").toHex());
		System.out.println(new Md5Hash("123","user").toHex());
		System.out.println(new Md5Hash("中国").toBase64());
		System.out.println(new Md5Hash("123").toString());
		System.out.println(new Sha1Hash("hello").toString().length());
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
	public void testPasswordService(){
		DefaultPasswordService service= new DefaultPasswordService();
		String str1= service.encryptPassword("123");
		String str2= service.encryptPassword("123");
		//两次加密的结果不会相同
		System.out.println("密码1str1="+str1+"");
		System.out.println("密码2str2="+str2+"");
		
		//匹配密码的也需要用提供的特定方
		//  $shiro1$SHA-256$500000$PlZFzu5/fGTNGNwsGVSloQ==$p74vvckECRDaWDBKAAnIhFZntCpyh0hV/ci2duzB9NQ=
		//	$shiro1$SHA-256$500000$5LW/1VVmC9+hJCTLblaMpQ==$RlYpjZEXAbXyAgXXT77u66KLPfPtcOsK3AADkxUWDs8=
		System.out.println(service.passwordsMatch("123", str1));
		
		
	}
	@Test
	public void testPasswordTestRealm(){
	
		Subject subject = login("admin","123","classpath:shiro-passwordtest.ini");
		
		System.out.println("正常返回");

	}

}
