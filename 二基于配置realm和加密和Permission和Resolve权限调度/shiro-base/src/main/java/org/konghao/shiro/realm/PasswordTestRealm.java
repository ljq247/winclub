package org.konghao.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class PasswordTestRealm  extends AuthorizingRealm {
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("是否返回!");
		/**
		 * 密码会在此处匹配,但是返回的时候,"111",系统还是会和Token里面的password进行匹配,加密匹配的方式在ini中进行!
		 */
		
		
	//  $shiro1$SHA-256$500000$PlZFzu5/fGTNGNwsGVSloQ==$p74vvckECRDaWDBKAAnIhFZntCpyh0hV/ci2duzB9NQ=
		String  passwordMatcher="$shiro1$SHA-256$500000$5LW/1VVmC9+hJCTLblaMpQ==$RlYpjZEXAbXyAgXXT77u66KLPfPtcOsK3AADkxUWDs8=";
		
		String p=new Md5Hash("123","user").toHex();
		System.out.println("new Md5Hash(123,user).toHex()生成的密码是:"+p);
		SimpleAuthenticationInfo info=	new SimpleAuthenticationInfo("ynkonghao@gmail.com", p, getName()+"_自定义");	
		
		info.setCredentialsSalt(ByteSource.Util.bytes("user"));  //设置加密言值
		
		return  info;
	}



}
