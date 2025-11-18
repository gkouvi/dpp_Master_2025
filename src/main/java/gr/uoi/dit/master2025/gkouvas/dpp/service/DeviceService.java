package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import org.springframework.stereotype.Service;

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

    /**
     * Retrieves all devices.
     *
     * @return list of DeviceDto
     */
    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a device by ID.
     *
     * @param id device ID
     * @return DeviceDto
     */
    public DeviceDto getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Device not found with id: " + id));
        return toDto(device);
    }

    /**
     * Creates a new device.
     *
     * @param dto device data
     * @return saved DeviceDto
     */
    public DeviceDto createDevice(DeviceDto dto) {

        Device device = new Device();
        device.setName(dto.getName());
        device.setType(dto.getType());
        device.setSerialNumber(dto.getSerialNumber());
        device.setInstallationDate(dto.getInstallationDate());
        device.setFirmwareVersion(dto.getFirmwareVersion());
        device.setStatus(dto.getStatus());

        if (dto.getBuildingId() != null) {
            Building building = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Building not found with id: " + dto.getBuildingId()));
            device.setBuilding(building);
        }

        Device saved = deviceRepository.save(device);
        return toDto(saved);
    }

    /**
     * Deletes a device by ID.
     *
     * @param id device ID
     */
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device not found with id: " + id);
        }
        deviceRepository.deleteById(id);
    }

    /**
     * Maps Device entity to DeviceDto.
     */
    private DeviceDto toDto(Device device) {
        DeviceDto dto = new DeviceDto();
        dto.setDeviceId(device.getDeviceId());
        dto.setName(device.getName());
        dto.setType(device.getType());
        dto.setSerialNumber(device.getSerialNumber());
        dto.setInstallationDate(device.getInstallationDate());
        dto.setFirmwareVersion(device.getFirmwareVersion());
        dto.setStatus(device.getStatus());
        if (device.getBuilding() != null) {
            dto.setBuildingId(device.getBuilding().getId());
        }
        return dto;
    }
}
