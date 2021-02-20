package com.cybertek.implementation;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.Role;
import com.cybertek.mapper.RoleMapper;
import com.cybertek.repository.RoleRepository;
import com.cybertek.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	RoleRepository repository;

	RoleMapper mapper;

	@Autowired
	public RoleServiceImpl(RoleRepository repository, RoleMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<RoleDTO> listAllRoles() {
		List<Role> list = repository.findAll();

		return list.stream().map(obj -> {
			return mapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

	@Override
	public RoleDTO findById(long id) {

		Role role = repository.findById(id).get();

		return mapper.convertToDto(role);
	}



}
