package com.pro24_group2.controller;
import com.pro24_group2.dto.request.UserRequest;
import com.pro24_group2.dto.response.UserResponse;
import com.pro24_group2.entity.User;
import com.pro24_group2.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

//    UserService userService1 = new UserServiceImpl();

	private final UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		UserResponse user = userService.getById(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping()
	public ResponseEntity<?> getAll(){
		List<UserResponse> all = userService.getAll();
		return ResponseEntity.ok(all);
	}

	@GetMapping("pagination")
	public ResponseEntity<Page<UserResponse>> getAll(
			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
			Pageable pageable
	){
		return ResponseEntity.ok(userService.getAll(pageable));
	}
	// HTTP Method POST, PUT, PATCH, DELETE

	// POST
	@PostMapping
	public ResponseEntity<?> userPOST(@RequestBody UserRequest user) {

		UserResponse userResponse = userService.create(user);
		return ResponseEntity.ok(userResponse);
	}

	//PUT
	@PutMapping("{id}")
	public ResponseEntity<?> userPUT(@PathVariable Long id, @RequestBody User user) {
		User user1 = userService.updateById(id, user);
		return ResponseEntity.ok(user1);
	}

	//Patch
	@PatchMapping
	public void userPATCH() {
		System.out.println("User update specific data");
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> userDELETE(@PathVariable Long id) {
		userService.deleteById(id);
		return ResponseEntity.ok(null);
	}

}