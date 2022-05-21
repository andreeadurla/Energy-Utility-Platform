package ds.assignment.rpc.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserDTO {

    private String token;
    private UUID id;
    private String username;
    private List<String> roles;
    private UUID clientId;
}
