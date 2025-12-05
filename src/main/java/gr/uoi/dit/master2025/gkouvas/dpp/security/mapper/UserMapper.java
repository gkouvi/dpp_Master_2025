package gr.uoi.dit.master2025.gkouvas.dpp.security.mapper;

import gr.uoi.dit.master2025.gkouvas.dpp.security.dto.RoleDto;
import gr.uoi.dit.master2025.gkouvas.dpp.security.dto.UserDto;
import gr.uoi.dit.master2025.gkouvas.dpp.security.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.isEnabled(),
                user.getRoles()
                        .stream()
                        .map(r -> new RoleDto(r.getId(), r.getName()))
                        .collect(Collectors.toSet())
        );
    }
}
