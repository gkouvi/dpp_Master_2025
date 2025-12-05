package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.CreateUserRequest;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.ResetPasswordRequest;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.RoleUpdateRequest;
import gr.uoi.dit.master2025.gkouvas.dpp.security.dto.UserDto;
import gr.uoi.dit.master2025.gkouvas.dpp.security.entity.Role;
import gr.uoi.dit.master2025.gkouvas.dpp.security.entity.User;
import gr.uoi.dit.master2025.gkouvas.dpp.security.mapper.UserMapper;
import gr.uoi.dit.master2025.gkouvas.dpp.security.repository.RoleRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }
    /*public List<User> getAllUsers() {
        return userRepo.findAll();
    }*/

    @PostMapping("/create")
    public User createUser(@RequestBody CreateUserRequest req) {

        User u = new User();
        u.setUsername(req.username());
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setEnabled(true);

        Set<Role> roles = roleRepo.findByNameIn(req.roles());
        u.setRoles(roles);

        return userRepo.save(u);
    }

    @PutMapping("/{id}/roles")
    public User updateRoles(@PathVariable Long id, @RequestBody RoleUpdateRequest req) {
        User u = userRepo.findById(id).orElseThrow();
        Set<Role> roles = roleRepo.findByNameIn(req.roles());
        u.setRoles(roles);
        return userRepo.save(u);
    }

    @PutMapping("/{id}/disable")
    public void disableUser(@PathVariable Long id) {
        User u = userRepo.findById(id).orElseThrow();
        u.setEnabled(false);
        userRepo.save(u);
    }

    @PutMapping("/{id}/reset-password")
    public void resetPassword(@PathVariable Long id, @RequestBody ResetPasswordRequest req) {
        User u = userRepo.findById(id).orElseThrow();
        u.setPassword(passwordEncoder.encode(req.newPassword()));
        userRepo.save(u);
    }
    @PutMapping("/users/{id}/enable")
    public ResponseEntity<?> setEnabled(@PathVariable Long id, @RequestBody EnableRequest req) {

        User user = userRepo.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        user.setEnabled(req.enabled());
        userRepo.save(user);

        return ResponseEntity.ok().build();
    }

    public record EnableRequest(boolean enabled){}

}

