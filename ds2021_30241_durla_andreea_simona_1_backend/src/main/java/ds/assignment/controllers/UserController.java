package ds.assignment.controllers;

import ds.assignment.dtos.rest.AdminDTO;
import ds.assignment.dtos.rest.UserDetailsDTO;
import ds.assignment.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<UUID> insertAdmin(@Valid @RequestBody AdminDTO adminDTO) {

        UUID userId = userService.insertAdmin(adminDTO);

        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<UUID> insertClient(@Valid @RequestBody UserDetailsDTO userDetailsDTO) {

        UUID userId = userService.insertClient(userDetailsDTO);

        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity deleteClient(@PathVariable("username") String username) {

        userService.deleteUserByUsername(username);

        return ResponseEntity.noContent().build();
    }
}
