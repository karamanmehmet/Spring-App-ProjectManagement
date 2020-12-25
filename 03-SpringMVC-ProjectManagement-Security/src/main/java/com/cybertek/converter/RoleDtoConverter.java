package com.cybertek.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cybertek.dto.RoleDTO;
import com.cybertek.service.RoleService;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {

	RoleService service;

	@Autowired
	public RoleDtoConverter(RoleService service) {
		this.service = service;
	}

	public RoleDTO convert(String source) {

		Integer id = Integer.parseInt(source);
		return service.findById(id);

	}

}
