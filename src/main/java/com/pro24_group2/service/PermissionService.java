package com.pro24_group2.service;

import com.pro24_group2.dto.request.PermissionRequest;
import com.pro24_group2.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
	PermissionResponse create(PermissionRequest request);

	List<PermissionResponse> getAll();

	PermissionResponse getById(Long id);

	PermissionResponse updateById(Long id, PermissionRequest request);

	void deleteById(Long id);
}