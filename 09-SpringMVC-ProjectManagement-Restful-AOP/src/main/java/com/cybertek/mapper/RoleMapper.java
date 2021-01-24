package com.cybertek.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.Role;
import com.cybertek.repository.RoleRepository;

@Component
public class RoleMapper {

	RoleRepository repository;

	@Autowired
	public RoleMapper(RoleRepository repository) {
		this.repository = repository;
	}

	public Role convertToEntity(RoleDTO dto) {

		return repository.findById(dto.getId()).get();

	}

	public RoleDTO convertToDto(Role role) {

		return new RoleDTO(role.getId(), role.getDescription());
	}
}
