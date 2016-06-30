package org.konghao.shiro.realm;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MapRealm implements Realm{
	private static Map<String,String> users;
	static{
		users = new HashMap<String, String>();
		users.put("kh", "1231");
		users.put("laozhang", "123");
	}

	public String getName() {
		return "mapRealm";
	}

	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}
	
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		String username = token.getPrincipal().toString();
		String password = new String((char[])token.getCredentials());
		System.out.println("账户名称和密码:"+username+":"+password);
				//是否存在某个key
		if(!users.containsKey(username)) throw new UnknownAccountException("用户名有错");
		if(!password.equals(users.get(username))) throw new IncorrectCredentialsException("密码错误");
//		
		AuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName()+"_自定义name");
		return info;
	}

}
