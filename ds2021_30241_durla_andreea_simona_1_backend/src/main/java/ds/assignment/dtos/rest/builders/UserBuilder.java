package ds.assignment.dtos.rest.builders;

import ds.assignment.dtos.rest.UserDTO;
import ds.assignment.dtos.rest.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserBuilder {

    private UserBuilder() {}

    public static UserDTO toUserDTO(String token, UserDetailsImpl userDetails) {

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return UserDTO.builder()
                .token(token)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .clientId(userDetails.getClientId())
                .build();
    }
}
