package ds.assignment.repositories;

import ds.assignment.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>, PagingAndSortingRepository<Client, UUID> {

    @Query("SELECT c FROM Client c WHERE c.deleted = false")
    List<Client> findAll();

    @Query("SELECT c FROM Client c WHERE c.deleted = false")
    Page<Client> findAll(Pageable var1);

    @Query("SELECT c FROM Client c WHERE c.id = ?1 AND c.deleted = false")
    Optional<Client> findById(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.deleted = true WHERE c.id = ?1")
    void deleteById(UUID id);
}
