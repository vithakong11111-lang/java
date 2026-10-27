package com.pro24_group2.controller;

import com.pro24_group2.dto.request.PermissionRequest;
import com.pro24_group2.dto.response.PermissionResponse;
import com.pro24_group2.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permissions")
public class PermissionController {

	private final PermissionService permissionService;

	PermissionController(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@PreAuthorize("hasAuthority('PERMISSION_READ')")
	@GetMapping
	public ResponseEntity<List<PermissionResponse>> getAll() {
		return ResponseEntity.ok(permissionService.getAll());
	}

	@PreAuthorize("hasAuthority('PERMISSION_READ')")
	@GetMapping("{id}")
	public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(permissionService.getById(id));
	}

	@PreAuthorize("hasAuthority('PERMISSION_WRITE')")
	@PostMapping
	public ResponseEntity<PermissionResponse> create(@Valid @RequestBody PermissionRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.create(request));
	}

	@PreAuthorize("hasAuthority('PERMISSION_WRITE')")
	@PutMapping("{id}")
	public ResponseEntity<PermissionResponse> update(@PathVariable Long id,
	                                                 @Valid @RequestBody PermissionRequest request) {
		return ResponseEntity.ok(permissionService.updateById(id, request));
	}

	@PreAuthorize("hasAuthority('PERMISSION_DELETE')")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		permissionService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}