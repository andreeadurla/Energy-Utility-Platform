package ds.assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "max_value")
    private float maxValue;

    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<MonitoredValue> monitoredValues;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}

