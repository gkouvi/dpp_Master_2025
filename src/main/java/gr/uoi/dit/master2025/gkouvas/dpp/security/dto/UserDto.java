package gr.uoi.dit.master2025.gkouvas.dpp.security.dto;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        boolean enabled,
        Set<RoleDto> roles
) {}
