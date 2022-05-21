package ds.assignment.controllers;

import ds.assignment.dtos.rest.AssignSensorToDeviceDTO;
import ds.assignment.dtos.rest.SensorDTO;
import ds.assignment.dtos.rest.SensorDetailsDTO;
import ds.assignment.services.interfaces.ISensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/sensors")
public class SensorController {

    private final ISensorService sensorService;

    @Autowired
    public SensorController(ISensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping()
    public ResponseEntity<Page<SensorDetailsDTO>> getSensors(Pageable pageable) {

        Page<SensorDetailsDTO> sensorDetailsDTOS = sensorService.findSensors(pageable);

        for (SensorDetailsDTO sensorDetailsDTO : sensorDetailsDTOS) {
            Link clientLink = linkTo(methodOn(SensorController.class)
                    .getSensor(sensorDetailsDTO.getId())).withSelfRel();
            sensorDetailsDTO.add(clientLink);
        }

        return new ResponseEntity<>(sensorDetailsDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SensorDetailsDTO> getSensor(@PathVariable("id") UUID id) {

        SensorDetailsDTO sensorDetailsDTO = sensorService.findSensorById(id);
        return new ResponseEntity<>(sensorDetailsDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertSensor(@Valid @RequestBody SensorDTO sensorDTO) {

        UUID sensorId = sensorService.insertSensor(sensorDTO);
        Link deviceLink = linkTo(methodOn(DeviceController.class)
                .getDevice(sensorId)).withSelfRel();

        return ResponseEntity
                .created(deviceLink.toUri())
                .body(sensorId);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateSensor(@PathVariable("id") UUID id,
                                       @Valid @RequestBody SensorDTO sensorDTO) {

        sensorService.updateSensor(id, sensorDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteSensor(@PathVariable("id") UUID id) {

        sensorService.deleteSensorById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/device/{id}")
    public ResponseEntity<SensorDetailsDTO> getSensorByDeviceId(@PathVariable("id") UUID deviceId) {

        SensorDetailsDTO sensorDetailsDTO = sensorService.findSensorByDeviceId(deviceId);
        return new ResponseEntity<>(sensorDetailsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/unassigned")
    public ResponseEntity<List<SensorDetailsDTO>> getUnassignedSensors() {

        List<SensorDetailsDTO> sensorDetailsDTOS = sensorService.findUnassignedSensors();
        return new ResponseEntity<>(sensorDetailsDTOS, HttpStatus.OK);
    }

    @PatchMapping(value = "/device-assigning")
    public ResponseEntity assignSensorToDevice(@Valid @RequestBody AssignSensorToDeviceDTO assignSensorToDeviceDTO) {

        sensorService.assignDeviceToSensor(assignSensorToDeviceDTO);
        return ResponseEntity.noContent().build();
    }
}
