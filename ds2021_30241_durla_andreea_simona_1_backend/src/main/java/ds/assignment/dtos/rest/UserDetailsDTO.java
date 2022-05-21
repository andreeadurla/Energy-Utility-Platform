package ds.assignment.dtos.rest;

import ds.assignment.dtos.rest.validators.annotations.UsernameConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDetailsDTO {

    @NotNull
    @UsernameConstraint
    private String username;

    @NotNull
    private String password;

    @NotNull
    private ClientDTO clientDetailsDTO;

    public void setUserId(UUID id) {
        clientDetailsDTO.setUserId(id);
    }
}
