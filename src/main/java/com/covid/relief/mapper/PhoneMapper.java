package com.covid.relief.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.covid.relief.dto.Phone;
import com.covid.relief.entity.PhoneEntity;

@Mapper
public interface PhoneMapper {

	@Mapping(target = "tweet", ignore = true)
	@Mapping(target = "id", ignore = true)
	Phone toModel(PhoneEntity phoneEntity);
	
	PhoneEntity toEntity(Phone phone);
	
}