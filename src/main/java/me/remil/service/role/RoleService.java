package me.remil.service.role;

import me.remil.entity.Role;

public interface RoleService {
	public void truncateTable(); 
	
	public void addRole(Role role);
}
