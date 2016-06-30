package org.konghao.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class MyPermissionResolver implements PermissionResolver {

	public Permission resolvePermission(String permissionString) {
		
		if(permissionString.startsWith("+")) {
			System.out.println("执行MyPermissionResolver--resolvePermission方法字符串为:"+permissionString);
			return new MyPermission(permissionString);
		}
		System.out.println("执行MyPermissionResolver--resolvePermission--WildcardPermission方法字符串为:"+permissionString);
		return new WildcardPermission(permissionString);
	}

}
