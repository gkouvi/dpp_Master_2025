package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import java.util.Set;

public record CreateUserRequest(String username, String password, Set<String> roles) {}

