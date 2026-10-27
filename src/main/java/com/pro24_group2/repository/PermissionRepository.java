package com.pro24_group2.repository;

import com.pro24_group2.entity.Permission;
//import org.hibernate.internal.util.Optional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	boolean existsByName(String name);

	Optional<Permission> findByName(String name);
}