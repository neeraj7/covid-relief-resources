package com.covid.relief.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid.relief.dto.Tweet;
import com.covid.relief.entity.CityEntity;
import com.covid.relief.mapper.PhoneMapper;
import com.covid.relief.mapper.TweetMapper;
import com.covid.relief.repository.CityRepository;
import com.covid.relief.repository.PhoneRepository;
import com.covid.relief.repository.TweetRepository;

@Service
public class TwitterService {

	@Autowired
	private TweetRepository tweetRepo;

	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private PhoneRepository phoneRepo;

	public List<Tweet> getAllSavedTweets(String city, String resource) {
		
		Optional<CityEntity> cityEntity = cityRepo.findByCity(city);
		
		if(cityEntity.isPresent()) {
			return tweetRepo.findByCitiesAndResourceOrderByCreatedAtDesc(cityEntity.get(), resource).stream().map(tweetEntity -> {
				Tweet tweet = Mappers.getMapper(TweetMapper.class).toModel(tweetEntity);
				tweet.setContacts(phoneRepo.findByTweetId(tweet.getId()).stream()
						.map(entity -> Mappers.getMapper(PhoneMapper.class).toModel(entity)).collect(Collectors.toSet()));
				return tweet;
			}).collect(Collectors.toList());
		}
		return new ArrayList<>();
		
	}
}