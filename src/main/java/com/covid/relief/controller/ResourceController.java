package com.covid.relief.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.covid.relief.dto.Resource;
import com.covid.relief.service.ResourceService;

@RestController
@RequestMapping("/resource")
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@GetMapping
	public List<Resource> getAll() {
		return resourceService.getAll();
	}
	
	@GetMapping("/{id}")
	public Resource getById(@PathVariable UUID id) {
		return resourceService.getById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Resource create(@RequestBody Resource resource) {
		return resourceService.create(resource);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable UUID id) {
        return resourceService.deleteById(id);
    }
	
	@PutMapping("/{id}")
    public Resource update(@RequestBody Resource resource, @PathVariable UUID id) {
		return resourceService.update(resource, id);
	}

}
