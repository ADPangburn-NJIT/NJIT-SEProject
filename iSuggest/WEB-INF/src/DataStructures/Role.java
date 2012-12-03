package DataStructures;

import Utils.TextUtils;

public class Role {
	private String roleId = null;
	private String role = null;
	
	public String getRoleId() {
		return roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRoleId(String roleId) {
		this.roleId = TextUtils.zeroToNull(roleId);
	}
	public void setRole(String role) {
		this.role = TextUtils.zeroToNull(role);
	}
}