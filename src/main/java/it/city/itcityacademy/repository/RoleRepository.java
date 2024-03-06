package it.city.itcityacademy.repository;

import it.city.itcityacademy.entity.Role;
import it.city.itcityacademy.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
}
