package com.pro24_group2.mapper;

import com.pro24_group2.dto.request.UserRequest;
import com.pro24_group2.dto.response.UserResponse;
import com.pro24_group2.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	// Convert UserRequest to User Entity
	public User toEntity(UserRequest request) {
		if (request == null) {
			return null;
		}

		User user = new User();
		user.setUsername(request.getUsername());
		user.setNickName(request.getNickName());
		user.setPasswordHash(request.getPasswordHash());
		user.setEnabled(request.isEnabled());

		return user;
	}

	// Convert User Entity to UserResponse
	public UserResponse toResponse(User user) {
		if (user == null) {
			return null;
		}

		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setUsername(user.getUsername());
		response.setNickName(user.getNickName());
		response.setEnabled(user.getEnabled());

		return response;
	}
}