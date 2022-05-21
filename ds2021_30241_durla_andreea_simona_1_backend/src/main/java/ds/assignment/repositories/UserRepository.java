package ds.assignment.repositories;

import ds.assignment.entities.Role;
import ds.assignment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.username = ?1 AND u.deleted = false")
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.deleted = true WHERE u.username = ?1")
    void deleteByUsername(String username);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.username = ?1 AND u.role = ?2 AND u.deleted = false")
    Optional<User> findByUsernameAndRole(String username, Role role);
}
