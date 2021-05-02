package com.covid.relief.mapper;

import org.mapstruct.Mapper;

import com.covid.relief.dto.Tweet;
import com.covid.relief.entity.TweetEntity;

@Mapper
public interface TweetMapper {

	Tweet toModel(TweetEntity tweetEntity);
	
	TweetEntity toEntity(Tweet tweet);
	
}