package com.covid.relief.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.covid.relief.dto.City;
import com.covid.relief.entity.CityEntity;

@Mapper
public interface CityMapper {

	@Mapping(target = "tweets", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "created", ignore = true)
	City toModel(CityEntity cityEntity);
	
	CityEntity toEntity(City city);
	
}