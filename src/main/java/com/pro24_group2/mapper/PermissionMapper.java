package com.pro24_group2.mapper;

import com.pro24_group2.dto.request.PermissionRequest;
import com.pro24_group2.dto.response.PermissionResponse;
import com.pro24_group2.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

	@Mapping(target = "id", ignore = true)
	Permission toEntity(PermissionRequest request);

	PermissionResponse toResponse(Permission permission);

	@Mapping(target = "id", ignore = true)
	void updateEntity(PermissionRequest request, @MappingTarget Permission permission);
}