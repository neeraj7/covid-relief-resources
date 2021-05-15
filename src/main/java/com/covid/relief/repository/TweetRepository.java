package com.covid.relief.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.covid.relief.entity.CityEntity;
import com.covid.relief.entity.TweetEntity;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, UUID>{

	Optional<TweetEntity> findByTweetId(long id);
	
	List<TweetEntity> findByText(String text);
	
	List<TweetEntity> findByCitiesAndResourceOrderByCreatedAtDesc(CityEntity city, String resource);
	
	List<TweetEntity> findByCitiesOrderByCreatedAtDesc(CityEntity city);
	
	List<TweetEntity> findByResourceOrderByCreatedAtDesc(String resource);
	
	@Transactional
	@Modifying
	void deleteByCreatedAtLessThan(Date createdAt);
	
}
