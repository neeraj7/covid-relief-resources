package com.covid.relief.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid.relief.entity.CityEntity;
import com.covid.relief.entity.TweetEntity;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, UUID>{

	Optional<TweetEntity> findByTweetId(long id);
	
	List<TweetEntity> findByCitiesAndResourceOrderByCreatedAtDesc(CityEntity city, String resource);
	
	List<TweetEntity> findByCitiesOrderByCreatedAtDesc(CityEntity city);
	
	List<TweetEntity> findByResourceOrderByCreatedAtDesc(String resource);
	
}
