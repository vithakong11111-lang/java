package com.pro24_group2.config;

import com.pro24_group2.entity.Permission;
import com.pro24_group2.entity.Role;
import com.pro24_group2.entity.User;
import com.pro24_group2.repository.PermissionRepository;
import com.pro24_group2.repository.RoleRepository;
import com.pro24_group2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

	private final PermissionRepository permissionRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminPassword;
	private final String userPassword;

	DataInitializer(PermissionRepository permissionRepository,
	                RoleRepository roleRepository,
	                UserRepository userRepository,
	                PasswordEncoder passwordEncoder,
	                @Value("${app.seed.admin-password:admin123}") String adminPassword,
	                @Value("${app.seed.user-password:dara123}") String userPassword) {
		this.permissionRepository = permissionRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminPassword = adminPassword;
		this.userPassword = userPassword;
	}

	@Override
	public void run(String... args) {
		// 1) 9 permissions: USER_, ROLE_, PERMISSION_ + READ, WRITE, DELETE
		List<Permission> all = new java.util.ArrayList<>();
		for (String group : List.of("USER", "ROLE", "PERMISSION")) {
			for (String action : List.of("READ", "WRITE", "DELETE")) {
				all.add(permission(group + "_" + action, action + " " + group.toLowerCase()));
			}
		}
		Permission userRead = all.get(0); // USER_READ

		// 2) Roles
		Role admin = role("ROLE_ADMIN", "Administrator", new HashSet<>(all));
		Role user = role("ROLE_USER", "Normal user", Set.of(userRead));

		// 3) Users: admin and dara
		user("admin", "Administrator", adminPassword, admin);
		user("dara", "Dara", userPassword, user);
	}

	private Permission permission(String name, String description) {
		return permissionRepository.findByName(name).orElseGet(() -> {
			Permission permission = new Permission();
			permission.setName(name);
			permission.setDescription(description);
			return permissionRepository.save(permission);
		});
	}

	private Role role(String name, String description, Set<Permission> permissions) {
		return roleRepository.findByName(name).orElseGet(() -> {
			Role role = new Role();
			role.setName(name);
			role.setDescription(description);
			role.setPermissions(new HashSet<>(permissions));
			return roleRepository.save(role);
		});
	}

	private void user(String username, String nickName, String rawPassword, Role role) {
		if (userRepository.existsByUsername(username)) {
			return;
		}
		User user = new User();
		user.setUsername(username);
		user.setNickName(nickName);
		user.setPasswordHash(passwordEncoder.encode(rawPassword));
		user.setEnabled(true);
		user.getRoles().add(role);
		userRepository.save(user);
	}
}