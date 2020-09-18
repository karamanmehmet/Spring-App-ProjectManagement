package com.cybertek.implementation;

import java.util.List;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.Role;

public interface RoleServiceImpl {
	
	public Role getRoleById(long id);
	
	public RoleDTO getRoleDTOById(long id);
	
	public List<Role> getRoles();
	
	public List<RoleDTO> getRoleDTOs();

}
