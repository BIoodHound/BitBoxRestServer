package com.bitboxserver.demo.Repositories;

import com.bitboxserver.demo.models.entities.Role;
import com.bitboxserver.demo.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
