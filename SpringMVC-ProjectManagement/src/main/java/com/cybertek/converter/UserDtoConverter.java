package com.cybertek.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.implementation.UserServiceImpl;
import com.cybertek.implementation.RoleServiceImpl;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<Object, UserDTO> {

	@Autowired
	UserServiceImpl service;

	public UserDTO convert(Object source) {
		String username = (String) source;
		UserDTO object = new UserDTO();
		try {
			object = service.getUserDTOByUsername(username);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

}
