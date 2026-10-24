package com.pro24_group2.service;

import com.pro24_group2.dto.request.UserRequest;
import com.pro24_group2.dto.response.UserResponse;
import com.pro24_group2.entity.User;

public interface UserService {
	UserResponse create(UserRequest user);
	UserResponse getById(Long id);
	User updateById(Long id, User user);
	void deleteById(Long id);
}