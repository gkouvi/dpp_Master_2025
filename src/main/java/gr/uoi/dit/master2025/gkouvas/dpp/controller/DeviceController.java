package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing devices.
 */
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Returns all devices.
     *
     * @return list of DeviceDto
     */
    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    /**
     * Returns a specific device by ID.
     *
     * @param id device ID
     * @return DeviceDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    /**
     * Creates a new device.
     *
     * @param dto device data
     * @return created DeviceDto
     */
    @PostMapping
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto dto) {
        DeviceDto created = deviceService.createDevice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Deletes a device.
     *
     * @param id device ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
