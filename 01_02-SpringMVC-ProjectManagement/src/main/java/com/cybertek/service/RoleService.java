package com.cybertek.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cybertek.data.DataGenerator;
import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Role;
import com.cybertek.implementation.RoleServiceImpl;

@Service
public class RoleService implements RoleServiceImpl {

	@Override
	public Role getRoleById(long id) {

		return DataGenerator.getRoles().stream().filter(o -> o.getId() == id).findFirst().get();

	}

	@Override
	public RoleDTO getRoleDTOById(long id) {

		Role role = DataGenerator.getRoles().stream().filter(o -> o.getId() == id).findFirst().get();
		return new RoleDTO(role.getId(), role.getDescription());

	}

	@Override
	public List<Role> getRoles() {
		return DataGenerator.getRoles();
	}

	@Override
	public List<RoleDTO> getRoleDTOs() {

		return DataGenerator.getRoles().stream().map(x -> {return new RoleDTO(x.getId(), x.getDescription());
					}).collect(Collectors.toList());
	}

}
