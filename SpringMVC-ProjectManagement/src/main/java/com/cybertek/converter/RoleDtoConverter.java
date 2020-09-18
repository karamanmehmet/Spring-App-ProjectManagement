package com.cybertek.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cybertek.dto.RoleDTO;
import com.cybertek.implementation.RoleServiceImpl;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<Object, RoleDTO> {

	@Autowired
	RoleServiceImpl service;
	

	public RoleDTO convert(Object source) {
		Integer id = Integer.parseInt((String)source);
		RoleDTO object=new RoleDTO();
		try {
			object = service.getRoleDTOById(id);
			
			System.out.println("Role Id :"+id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

}
