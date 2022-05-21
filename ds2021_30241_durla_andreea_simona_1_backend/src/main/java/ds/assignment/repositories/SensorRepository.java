package ds.assignment.repositories;

import ds.assignment.entities.Sensor;
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
public interface SensorRepository extends JpaRepository<Sensor, UUID>, PagingAndSortingRepository<Sensor, UUID> {

    @Query("SELECT s FROM Sensor s WHERE s.deleted = false")
    List<Sensor> findAll();

    @Query("SELECT s FROM Sensor s WHERE s.deleted = false")
    Page<Sensor> findAll(Pageable var1);

    @Query("SELECT s FROM Sensor s WHERE s.id = ?1 AND s.deleted = false")
    Optional<Sensor> findById(UUID id);

    @Query("SELECT s FROM Sensor s WHERE s.device.id = ?1 AND s.deleted = false")
    Optional<Sensor> findByDeviceId(UUID deviceId);

    @Query("SELECT s FROM Sensor s WHERE s.device.id = null AND s.deleted = false")
    List<Sensor> findUnassignedSensors();

    @Query("SELECT s FROM Sensor s WHERE s.device.client.id = ?1 AND s.deleted = false")
    List<Sensor> findByClientId(UUID clientId);

    @Modifying
    @Transactional
    @Query("UPDATE Sensor s SET s.device.id = null WHERE s.device.id = ?1")
    void unassignDevice(UUID deviceId);

    @Modifying
    @Transactional
    @Query("UPDATE Sensor s SET s.deleted = true WHERE s.id = ?1")
    void deleteById(UUID id);
}
