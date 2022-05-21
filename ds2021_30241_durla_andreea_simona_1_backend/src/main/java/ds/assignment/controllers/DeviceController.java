package ds.assignment.controllers;

import ds.assignment.dtos.rest.*;
import ds.assignment.services.interfaces.IDeviceService;
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
@RequestMapping(value = "/devices")
public class DeviceController {

    private final IDeviceService deviceService;

    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<Page<DeviceDetailsDTO>> getDevices(Pageable pageable) {

        Page<DeviceDetailsDTO> deviceDTOS = deviceService.findDevices(pageable);

        for (DeviceDetailsDTO deviceDTO : deviceDTOS) {
            Link clientLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(deviceDTO.getId())).withSelfRel();
            deviceDTO.add(clientLink);
        }

        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable("id") UUID id) {

        DeviceDetailsDTO deviceDTO = deviceService.findDeviceById(id);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDTO deviceDTO) {

        UUID deviceId = deviceService.insertDevice(deviceDTO);
        Link deviceLink = linkTo(methodOn(DeviceController.class)
                            .getDevice(deviceId)).withSelfRel();

        return ResponseEntity
                .created(deviceLink.toUri())
                .body(deviceId);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateDevice(@PathVariable("id") UUID id,
                                       @Valid @RequestBody DeviceDTO deviceDTO) {

        deviceService.updateDevice(id, deviceDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteDevice(@PathVariable("id") UUID id) {

        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<List<DeviceDetailsDTO>> getDevicesByClientId(@PathVariable("id") UUID clientId) {

        List<DeviceDetailsDTO> deviceDTO = deviceService.findDevicesByClientId(clientId);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/client/{id}/more-details")
    public ResponseEntity<List<DeviceMoreDetailsDTO>> getDevicesWithMoreDetailsByClientId(@PathVariable("id") UUID clientId) {

        List<DeviceMoreDetailsDTO> deviceDTO = deviceService.findDevicesWithMoreDetailsByClientId(clientId);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/client/{id}/some-details")
    public ResponseEntity<List<DeviceSomeDetailsDTO>> getDevicesWithSomeDetailsByClientId(@PathVariable("id") UUID clientId) {

        List<DeviceSomeDetailsDTO> deviceDTO = deviceService.findDevicesWithSomeDetailsByClientId(clientId);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/unassigned")
    public ResponseEntity<List<DeviceDetailsDTO>> getUnassignedDevices() {

        List<DeviceDetailsDTO> deviceDetailsDTOS = deviceService.findUnassignedDevices();
        return new ResponseEntity<>(deviceDetailsDTOS, HttpStatus.OK);
    }

    @PatchMapping(value = "/client-assigning")
    public ResponseEntity assignClientToDevices(@Valid @RequestBody AssignClientToDevicesDTO assignClientToDevicesDTO) {

        deviceService.assignClientToDevices(assignClientToDevicesDTO);
        return ResponseEntity.noContent().build();
    }
}
