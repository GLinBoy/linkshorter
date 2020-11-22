package com.glibnoy.linkshorter.service.mapper;

import org.mapstruct.Mapper;

import com.glibnoy.linkshorter.entity.Url;
import com.glibnoy.linkshorter.service.dto.UrlDTO;

@Mapper(componentModel = "spring", uses = {})
public interface UrlMapper extends EntityMapper<UrlDTO, Url> {

	default Url fromId(Long id) {
		if (id == null) {
			return null;
		}
		Url menu = new Url();
		menu.setId(id);
		return menu;
	}
}
