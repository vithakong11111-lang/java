package com.pro24_group2.service.impl;

import com.pro24_group2.dto.request.PermissionRequest;
import com.pro24_group2.dto.response.PermissionResponse;
import com.pro24_group2.entity.Permission;
import com.pro24_group2.exception.NotFoundException;
import com.pro24_group2.mapper.PermissionMapper;
import com.pro24_group2.repository.PermissionRepository;
import com.pro24_group2.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	PermissionServiceImpl(PermissionRepository permissionRepository,
	                      PermissionMapper permissionMapper) {
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
	}

	@Override
	public PermissionResponse create(PermissionRequest request) {
		if (permissionRepository.existsByName(request.getName())) {
			throw new IllegalArgumentException("Permission already exists: " + request.getName());
		}
		Permission saved = permissionRepository.save(permissionMapper.toEntity(request));
		return permissionMapper.toResponse(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PermissionResponse> getAll() {
		return permissionRepository.findAll().stream()
				.map(permissionMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public PermissionResponse getById(Long id) {
		return permissionMapper.toResponse(findOrThrow(id));
	}

	@Override
	public PermissionResponse updateById(Long id, PermissionRequest request) {
		Permission permission = findOrThrow(id);
		if (!permission.getName().equals(request.getName())
				&& permissionRepository.existsByName(request.getName())) {
			throw new IllegalArgumentException("Permission already exists: " + request.getName());
		}
		permissionMapper.updateEntity(request, permission);
		return permissionMapper.toResponse(permissionRepository.save(permission));
	}

	@Override
	public void deleteById(Long id) {
		Permission permission = findOrThrow(id);
		permissionRepository.delete(permission);
		permissionRepository.flush(); // a permission still used by a role fails here, so the handler returns 409
	}

	private Permission findOrThrow(Long id) {
		return permissionRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Permission not found with id: " + id));
	}
}