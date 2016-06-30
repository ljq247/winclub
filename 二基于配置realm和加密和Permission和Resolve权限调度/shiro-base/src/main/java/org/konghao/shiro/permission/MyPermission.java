package org.konghao.shiro.permission;

import org.apache.shiro.authz.Permission;

public class MyPermission implements Permission {
	private String resourceId;
	private String operator;  //操作
	private String instanceId;  //实例
	
	public String  wildString;  //保存原生态的字符
	
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	

	public MyPermission() {
	}
	
	@Override
	public String toString() {
		return "MyPermission [resourceId=" + resourceId + ", operator="
				+ operator + ", instanceId=" + instanceId + "]";
	}

	public MyPermission(String permissionStr) {
		this.wildString=permissionStr;
		String[] strs = permissionStr.split("\\+");
		if(strs.length>1) {
			this.setResourceId(strs[1]);
		}
		if(this.getResourceId()==null||"".equals(this.getResourceId())) {
			this.setResourceId("*");
		}
		
		if(strs.length>2) {
			this.setOperator(strs[2]);
		}
		
		if(strs.length>3) {
			this.setInstanceId(strs[3]);
		}
		
		if(this.getInstanceId()==null||"".equals(this.getInstanceId())) {
			this.setInstanceId("*");
		}
	}
	/**
	 *在这里进行匹配
	 */
	public boolean implies(Permission p) {
		if(!(p instanceof MyPermission)) return false;
		MyPermission p1= (MyPermission) p;
		System.out.println("implies方法本地字符:"+this.wildString+"---匹配的字符:"+p1.wildString);
		MyPermission mp = (MyPermission)p;
		if(!this.getResourceId().equals("*")&&!mp.getResourceId().equals(this.getResourceId()))
			return false;
		if(!this.getOperator().equals("*")&&!mp.getOperator().equals(operator))
			return false;
		if(!this.getInstanceId().equals("*")&&!mp.getInstanceId().equals(instanceId))
			return false;
		return true;
	}

}
