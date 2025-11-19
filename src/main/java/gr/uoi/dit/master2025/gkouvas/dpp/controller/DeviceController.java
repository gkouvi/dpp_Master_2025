package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceDto;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.service.DeviceService;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for managing devices.
 */
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceRepository deviceRepo;

    public DeviceController(DeviceService deviceService, DeviceRepository deviceRepo) {
        this.deviceService = deviceService;
        this.deviceRepo = deviceRepo;
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @PostMapping
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.createDevice(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/building/{buildingId}")
    public List<DeviceDto> getDevicesByBuilding(@PathVariable Long buildingId) {
        return deviceService.getDevicesByBuilding(buildingId);
    }

    // ---------- QR UPLOAD ----------
    @PostMapping(value = "/{id}/qr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadQr(@PathVariable Long id,
                                         @RequestParam("file") MultipartFile file) throws Exception {

        Device d = deviceRepo.findById(id).orElseThrow();
        d.setQrCode(file.getBytes());
        deviceRepo.save(d);
        return ResponseEntity.ok().build();
    }

    // -------- QR RETRIEVE ----------
    @GetMapping(value = "/qr/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQr(@PathVariable Long id) {
        return deviceRepo.findById(id)
                .map(Device::getQrCode)
                .orElse(null);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> updateDevice(
            @PathVariable Long id,
            @RequestBody DeviceDto dto) {

        DeviceDto updated = deviceService.updateDevice(id, dto);
        return ResponseEntity.ok(updated);
    }

}
