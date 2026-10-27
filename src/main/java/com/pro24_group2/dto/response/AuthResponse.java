package com.pro24_group2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

	private String accessToken;
	private String tokenType = "Bearer";

	public AuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}