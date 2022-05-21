package ds.assignment.repositories;

import ds.assignment.entities.Device;
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
public interface DeviceRepository extends JpaRepository<Device, UUID>, PagingAndSortingRepository<Device, UUID> {

    @Query("SELECT d FROM Device d WHERE d.deleted = false")
    List<Device> findAll();

    @Query("SELECT d FROM Device d WHERE d.deleted = false")
    Page<Device> findAll(Pageable var1);

    @Query("SELECT d FROM Device d WHERE d.id = ?1 AND d.deleted = false")
    Optional<Device> findById(UUID id);

    @Query("SELECT d FROM Device d WHERE d.client.id = ?1 AND d.deleted = false")
    List<Device> findByClientId(UUID clientId);

    @Query("SELECT d FROM Device d WHERE d.client.id = null AND d.deleted = false")
    List<Device> findUnassignedDevices();

    @Query("SELECT d FROM Device d WHERE d.id IN (?1) AND d.deleted = false")
    List<Device> findByIds(List<UUID> ids);

    @Modifying
    @Transactional
    @Query("UPDATE Device d SET d.client.id = null WHERE d.client.id = ?1")
    void unassignClient(UUID clientId);

    @Modifying
    @Transactional
    @Query("UPDATE Device d SET d.deleted = true WHERE d.id = ?1")
    void deleteById(UUID id);
}
