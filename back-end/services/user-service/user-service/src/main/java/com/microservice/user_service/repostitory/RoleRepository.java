package com.microservice.user_service.repostitory;

import com.microservice.user_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.username = :username")
    List<Role> findRolesByUsername(@Param("username") String username);
}

