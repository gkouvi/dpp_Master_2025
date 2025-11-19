package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.util.QRGenerator;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing Device entities.
 */
@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final BuildingRepository buildingRepository;

    public DeviceService(DeviceRepository deviceRepository,
                         BuildingRepository buildingRepository) {
        this.deviceRepository = deviceRepository;
        this.buildingRepository = buildingRepository;
    }

    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public DeviceDto getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Δεν βρέθηκε συσκευή: " + id));
        return toDto(device);
    }

    public DeviceDto createDevice(DeviceDto dto) {
        Device d = new Device();
        d.setName(dto.getName());
        d.setType(dto.getType());
        d.setSerialNumber(dto.getSerialNumber());
        d.setInstallationDate(dto.getInstallationDate());
        d.setFirmwareVersion(dto.getFirmwareVersion());
        d.setStatus(dto.getStatus());
        d.setBuilding(buildingRepository.findById(dto.getBuildingId()).orElseThrow());

        Device saved = deviceRepository.save(d);
        return toDto(saved);
    }

    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Δεν βρέθηκε συσκευή: " + id);
        }
        deviceRepository.deleteById(id);
    }

    public List<DeviceDto> getDevicesByBuilding(Long buildingId) {
        return deviceRepository.findByBuildingId(buildingId)
                .stream()
                .map(DeviceDto::new)
                .toList();
    }

    private DeviceDto toDto(Device device) {
        DeviceDto dto = new DeviceDto(device);
        return dto;
    }

    public DeviceDto updateDevice(Long id, DeviceDto dto) {

        Device d = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Δεν βρέθηκε συσκευή: " + id));

        d.setName(dto.getName());
        d.setType(dto.getType());
        d.setSerialNumber(dto.getSerialNumber());
        d.setFirmwareVersion(dto.getFirmwareVersion());
        d.setInstallationDate(dto.getInstallationDate());
        d.setStatus(dto.getStatus());

        // buildingId προαιρετικά
        if (dto.getBuildingId() != null) {
            Building b = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow();
            d.setBuilding(b);
        }

        Device saved = deviceRepository.save(d);

        return new DeviceDto(saved);
    }

}
