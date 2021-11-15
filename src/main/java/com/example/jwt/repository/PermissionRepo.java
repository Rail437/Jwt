package com.example.jwt.repository;

import com.example.jwt.model.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<PermissionEntity, Long> {

}
