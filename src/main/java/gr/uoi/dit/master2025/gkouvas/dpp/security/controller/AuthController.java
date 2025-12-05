package gr.uoi.dit.master2025.gkouvas.dpp.security.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.security.dto.LoginRequest;
import gr.uoi.dit.master2025.gkouvas.dpp.security.dto.LoginResponse;

import gr.uoi.dit.master2025.gkouvas.dpp.security.entity.User;
import gr.uoi.dit.master2025.gkouvas.dpp.security.repository.UserRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.security.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepo,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {



        User user = userRepo.findByUsername(request.username())
                .orElse(null);

        if (user == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        // Φτιάχνουμε UserDetails για το JWT
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword()) // ήδη encoded
                        .authorities(
                                user.getRoles().stream()
                                        .map(r -> "ROLE_" + r.getName())
                                        .toArray(String[]::new)
                        )
                        .build();

        String token = jwtService.generateToken(userDetails);



        return ResponseEntity.ok(new LoginResponse(token));
    }
}
