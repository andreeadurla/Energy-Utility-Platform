package ds.assignment.services.interfaces;

import ds.assignment.dtos.rest.AdminDTO;
import ds.assignment.dtos.rest.UserDetailsDTO;

import java.util.UUID;

public interface IUserService {

    UUID insertAdmin(AdminDTO adminDTO);
    UUID insertClient(UserDetailsDTO userDetailsDTO);
    void deleteUserByUsername(String username);
}
