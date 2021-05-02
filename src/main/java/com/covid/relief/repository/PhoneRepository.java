package com.covid.relief.repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid.relief.entity.PhoneEntity;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, UUID>{

	Optional<PhoneEntity> findByPhoneNumber(long phoneNumber);
	
	Set<PhoneEntity> findByTweetId(UUID tweetId);
}
