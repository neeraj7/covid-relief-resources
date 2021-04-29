package com.covid.relief.mapper;

import org.mapstruct.Mapper;

import com.covid.relief.dto.Resource;
import com.covid.relief.entity.ResourceEntity;

@Mapper
public interface ResourceMapper {

	Resource toModel(ResourceEntity resourceEntity);
	
	ResourceEntity toEntity(Resource resource);
	
}