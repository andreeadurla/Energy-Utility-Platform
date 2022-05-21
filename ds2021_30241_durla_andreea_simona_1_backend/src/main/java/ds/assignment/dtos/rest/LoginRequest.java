package ds.assignment.dtos.rest;

import ds.assignment.dtos.rest.validators.annotations.UsernameConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull
    @UsernameConstraint
    private String username;

    @NotNull
    private String password;
}
