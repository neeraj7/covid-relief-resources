package com.covid.relief.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid.relief.entity.TweetEntity;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, UUID>{

	Optional<TweetEntity> findByTweetId(long id);
	
}
