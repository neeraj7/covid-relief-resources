package com.covid.relief.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.covid.relief.dto.Tweet;
import com.covid.relief.entity.CityEntity;
import com.covid.relief.mapper.TweetMapper;
import com.covid.relief.repository.CityRepository;
import com.covid.relief.repository.TweetRepository;

@Service
public class TwitterService {

	@Autowired
	private TweetRepository tweetRepo;

	@Autowired
	private CityRepository cityRepo;

	public List<Tweet> getAllSavedTweets(String city, String resource) {
		if (StringUtils.hasText(city) && StringUtils.hasText(resource)) {
			city = city.toLowerCase();
			resource = resource.toLowerCase();
			Optional<CityEntity> cityEntity = cityRepo.findByCity(city);
			if (cityEntity.isPresent()) {
				return tweetRepo.findByCitiesAndResourceOrderByCreatedAtDesc(cityEntity.get(), resource).stream()
						.map(tweetEntity -> Mappers.getMapper(TweetMapper.class).toModel(tweetEntity))
						.collect(Collectors.toList());
			}
		} else if (StringUtils.hasText(city)) {
			city = city.toLowerCase();
			Optional<CityEntity> cityEntity = cityRepo.findByCity(city);
			if (cityEntity.isPresent()) {
				return tweetRepo.findByCitiesOrderByCreatedAtDesc(cityEntity.get()).stream()
						.map(tweetEntity -> Mappers.getMapper(TweetMapper.class).toModel(tweetEntity))
						.collect(Collectors.toList());
			}

		} else if (StringUtils.hasText(resource)) {
			resource = resource.toLowerCase();
			return tweetRepo.findByResourceOrderByCreatedAtDesc(resource).stream()
					.map(tweetEntity -> Mappers.getMapper(TweetMapper.class).toModel(tweetEntity))
					.collect(Collectors.toList());
		}

		return tweetRepo.findAll(Sort.by("createdAt").descending()).stream()
				.map(tweetEntity -> Mappers.getMapper(TweetMapper.class).toModel(tweetEntity))
				.collect(Collectors.toList());

	}

}