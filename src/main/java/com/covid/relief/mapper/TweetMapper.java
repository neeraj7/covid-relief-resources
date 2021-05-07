package com.covid.relief.mapper;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;

import com.covid.relief.dto.Tweet;
import com.covid.relief.entity.TweetEntity;

@Mapper(uses = {CityMapper.class})
public interface TweetMapper {

	@Mapping(source = "createdAt", qualifiedByName = "calculateOlder", target = "older")
	@Mapping(target = "contacts", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "cities", ignore = true)
	@Mapping(target = "resource", ignore = true)
	@Mapping(target = "id", ignore = true)
	Tweet toModel(TweetEntity tweetEntity);
	
	TweetEntity toEntity(Tweet tweet);
	
	// come back later
	@Named("calculateOlder")
	default String calculateOlder(Date createdAt) {
		return new PrettyTime().format(createdAt);
	}
	
}