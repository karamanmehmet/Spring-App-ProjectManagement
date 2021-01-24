package com.cybertek.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.service.ProjectService;

@Component
@ConfigurationPropertiesBinding
public class ProjectDtoConverter implements Converter<String, ProjectDTO> {

	@Autowired
	ProjectService service;

	@Override
	public ProjectDTO convert(String source) {

		ProjectDTO p= service.getByProjectCode(source);
		
		return p;
	}

}
