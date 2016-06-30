package org.konghao.shiro.permission;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class MyRolePermissionResolver implements RolePermissionResolver {
	
	/**
	 * 当用户具有角色的时候, 权限集合,为这个用户添加权限;
	 * 需要在ini文件中配置
	 */
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		if(roleString.contains("r1")) {
			return Arrays.asList((Permission)new WildcardPermission("classroom:*"));
		}
		return null;
	}

}
