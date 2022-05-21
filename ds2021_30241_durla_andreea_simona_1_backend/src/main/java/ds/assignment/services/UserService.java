package ds.assignment.services;

import ds.assignment.dtos.rest.AdminDTO;
import ds.assignment.dtos.rest.UserDetailsDTO;
import ds.assignment.dtos.rest.UserDetailsImpl;
import ds.assignment.entities.Role;
import ds.assignment.entities.User;
import ds.assignment.exceptions.models.DuplicateResourceException;
import ds.assignment.exceptions.models.ResourceNotFoundException;
import ds.assignment.repositories.UserRepository;
import ds.assignment.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       ClientService clientService) {
        this.userRepository = userRepository;
        this.clientService = clientService;
    }

    public UUID insertAdmin(AdminDTO adminDTO) {
        Optional<User> userOptional = userRepository.findByUsername(adminDTO.getUsername());

        if(userOptional.isPresent())
            throw new DuplicateResourceException(User.class.getSimpleName() + " with username: " + adminDTO.getUsername());

        User user = User.builder()
                .username(adminDTO.getUsername())
                .password(passwordEncoder.encode(adminDTO.getPassword()))
                .role(Role.ADMIN)
                .build();

        user = userRepository.save(user);

        return user.getId();
    }

    public UUID insertClient(UserDetailsDTO userDetailsDTO) {

        Optional<User> userOptional = userRepository.findByUsername(userDetailsDTO.getUsername());

        if(userOptional.isPresent())
            throw new DuplicateResourceException(User.class.getSimpleName() + " with username: " + userDetailsDTO.getUsername());

        User user = User.builder()
                        .username(userDetailsDTO.getUsername())
                        .password(passwordEncoder.encode(userDetailsDTO.getPassword()))
                        .role(Role.CLIENT)
                        .build();

        user = userRepository.save(user);

        userDetailsDTO.setUserId(user.getId());
        clientService.insertClient(userDetailsDTO.getClientDetailsDTO());

        return user.getId();
    }

    public void deleteUserByUsername(String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if(!userOptional.isPresent()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username: " + username);
        }

        userRepository.deleteByUsername(username);
        clientService.deleteClientById(userOptional.get().getClientId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName() + " with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
