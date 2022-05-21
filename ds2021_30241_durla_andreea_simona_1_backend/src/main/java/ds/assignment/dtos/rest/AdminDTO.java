package ds.assignment.dtos.rest;

import ds.assignment.dtos.rest.validators.annotations.UsernameConstraint;
import lombok.Data;

@Data
public class AdminDTO {

    @UsernameConstraint
    private String username;
    private String password;
}
