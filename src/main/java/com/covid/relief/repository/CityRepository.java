package com.covid.relief.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid.relief.entity.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, UUID>{
	
	Optional<CityEntity> findByCity(String city);
}