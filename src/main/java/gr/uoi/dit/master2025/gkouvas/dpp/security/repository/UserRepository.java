package gr.uoi.dit.master2025.gkouvas.dpp.security.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
