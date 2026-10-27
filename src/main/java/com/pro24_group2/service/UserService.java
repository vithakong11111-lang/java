package com.pro24_group2.service;

import com.pro24_group2.dto.request.UserRequest;
import com.pro24_group2.dto.response.UserResponse;
import com.pro24_group2.entity.User;
import org.springframework.data.domain.Page;

//import java.awt.print.Pageable;
import java.util.List;

public interface UserService {
	UserResponse create(UserRequest user);

	List<UserResponse> getAll();

	Page<UserResponse> getAll(org.springframework.data.domain.Pageable pageable);

	UserResponse getById(Long id);
	User updateById(Long id, User user);
	void deleteById(Long id);

	// GET  Data by list
//	Page<UserResponse> getAll(Pageable pageable);
}