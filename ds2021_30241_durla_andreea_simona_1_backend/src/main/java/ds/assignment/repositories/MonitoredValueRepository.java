package ds.assignment.repositories;

import ds.assignment.entities.MonitoredValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonitoredValueRepository extends JpaRepository<MonitoredValue, UUID>, PagingAndSortingRepository<MonitoredValue, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE MonitoredValue m SET m.deleted = true WHERE m.sensor.id = ?1")
    void deleteBySensorId(UUID sensorId);

    Page<MonitoredValue> findAllByTimestampBetweenAndDeletedIsFalseAndSensorIdInOrderByTimestamp(Timestamp timestampStart,
                                                                                                 Timestamp timestampEnd,
                                                                                                 List<UUID> sensorIds,
                                                                                                 Pageable pageable);

    Page<MonitoredValue> findAllBySensorIdAndTimestampBetweenAndDeletedIsFalseOrderByTimestamp(UUID sensorId,
                                                                                               Timestamp timestampStart,
                                                                                               Timestamp timestampEnd,
                                                                                               Pageable pageable);

    List<MonitoredValue> findAllBySensorIdAndTimestampBetweenAndDeletedIsFalseOrderByTimestamp(UUID sensorId,
                                                                                               Timestamp timestampStart,
                                                                                               Timestamp timestampEnd);

    Optional<MonitoredValue> findTopBySensorIdAndTimestampBetweenAndDeletedFalseOrderByTimestampDesc(UUID sensorId,
                                                                                                     Timestamp timestampStart,
                                                                                                     Timestamp timestampEnd);

    Optional<MonitoredValue> findTopBySensorIdAndDeletedFalseOrderByTimestampDesc(UUID sensorId);
}