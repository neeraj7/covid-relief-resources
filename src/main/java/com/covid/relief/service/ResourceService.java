package com.covid.relief.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.covid.relief.dto.Resource;
import com.covid.relief.entity.ResourceEntity;
import com.covid.relief.exception.ApiError;
import com.covid.relief.exception.ApiRuntimeException;
import com.covid.relief.mapper.ResourceMapper;
import com.covid.relief.repository.ResourceRepository;

@Service
public class ResourceService {
	
	@Autowired
	private ResourceRepository resourceRepo;
	
	public Resource create(Resource resource) {
		ResourceEntity resourceEntity = Mappers.getMapper(ResourceMapper.class).toEntity(resource);
		return Mappers.getMapper(ResourceMapper.class).toModel(resourceRepo.save(resourceEntity));
	}
	
	public Resource update(Resource resource, UUID id) {
		Optional<ResourceEntity> resourceEntity = resourceRepo.findById(id);
		if(resourceEntity.isPresent()) {
			resource.setId(resourceEntity.get().getId());
			return Mappers.getMapper(ResourceMapper.class).toModel(Mappers.getMapper(ResourceMapper.class).toEntity(resource));
		}
		throw new ApiRuntimeException(HttpStatus.NOT_FOUND, "Resource not found with id: " + id);
//		throw new ApiException(HttpStatus.NOT_FOUND, "Resource not found with id: " + id);
	}
	
	public Resource getById(UUID id) {
		Optional<ResourceEntity> resourceEntity = resourceRepo.findById(id);
		if(resourceEntity.isPresent()) {
			return Mappers.getMapper(ResourceMapper.class).toModel(resourceEntity.get());
		}
		throw new ApiRuntimeException(HttpStatus.NOT_FOUND, "Resource not found with id: " + id);
//		throw new ApiException(HttpStatus.NOT_FOUND, "Resource not found with id: " + id);
	}
	
	public List<Resource> getAll() {
		return resourceRepo.findAll().stream()
				.map(entity -> Mappers.getMapper(ResourceMapper.class).toModel(entity))
				.collect(Collectors.toList());
	}
	
	public boolean deleteById(UUID id) {
		resourceRepo.deleteById(id);
		return true;
	}
	
}