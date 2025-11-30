

package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceKpiDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.UpcomingMaintenanceItemDto;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.service.DeviceService;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing devices.
 */
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceRepository deviceRepo;

    public DeviceController(DeviceService deviceService,
                            DeviceRepository deviceRepo) {
        this.deviceService = deviceService;
        this.deviceRepo = deviceRepo;
    }

    @GetMapping
    public List<DeviceDto> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public DeviceDto getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
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

    @PostMapping
    public DeviceDto createDevice(@RequestBody DeviceDto dto) {
        return deviceService.createDevice(dto);
    }

    @PutMapping("/{id}")
    public DeviceDto updateDevice(@PathVariable Long id,
                                  @RequestBody DeviceDto dto) {
        return deviceService.updateDevice(id, dto);
    }

    // ---------- QR UPLOAD ----------
    @PostMapping(value = "/{id}/qr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadQr(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws Exception {

        Device d = deviceRepo.findById(id).orElseThrow();
        d.setQrCode(file.getBytes());
        deviceRepo.save(d);
        return ResponseEntity.ok().build();
    }

    // ---------- QR RETRIEVE ----------
    @GetMapping(value = "/qr/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQr(@PathVariable Long id) {
        return deviceRepo.findById(id)
                .map(Device::getQrCode)
                .orElse(null);
    }

    @GetMapping("/upcoming-maintenance")
    public List<DeviceDto> getUpcomingMaintenance() {
        return deviceService.getUpcomingMaintenance();
    }

    @GetMapping("/upcoming-maintenance/details")
    public List<UpcomingMaintenanceItemDto> getUpcomingMaintenanceDetails() {
        return deviceService.getUpcomingMaintenanceDetails();
    }

    @GetMapping("/maintenance-kpis")
    public MaintenanceKpiDto getMaintenanceKpis() {
        return deviceService.getMaintenanceKpis();
    }


}
