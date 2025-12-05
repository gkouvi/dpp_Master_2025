package gr.uoi.dit.master2025.gkouvas.dpp.security.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    Set<Role> findByNameIn(Set<String> names);
}
