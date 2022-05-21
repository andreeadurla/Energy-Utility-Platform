package ds.assignment.dtos.rest;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDTO {

    private String token;
    private UUID id;
    private String username;
    private List<String> roles;
    private UUID clientId;
}
