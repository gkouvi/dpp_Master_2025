package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceLogDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.MaintenanceLog;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing MaintenanceLog entities.
 */
@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final DeviceRepository deviceRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              DeviceRepository deviceRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * Retrieves all maintenance logs.
     *
     * @return list of MaintenanceLogDto
     */
    public List<MaintenanceLogDto> getAllLogs() {
        return maintenanceRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves maintenance logs for a specific device.
     *
     * @param deviceId the device ID
     * @return list of MaintenanceLogDto
     */
    public List<MaintenanceLogDto> getLogsByDevice(Long deviceId) {
        return maintenanceRepository.findByDevice_DeviceId(deviceId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new maintenance log entry.
     *
     * @param dto DTO with maintenance data
     * @return saved MaintenanceLogDto
     */
    public MaintenanceLogDto createLog(MaintenanceLogDto dto) {
        Device device = deviceRepository.findById(dto.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Device not found with id: " + dto.getDeviceId()));

        MaintenanceLog log = new MaintenanceLog();
        log.setDevice(device);
        log.setMaintenanceDate(dto.getMaintenanceDate());
        log.setDescription(dto.getDescription());
        log.setTechnician(dto.getTechnician());

        MaintenanceLog saved = maintenanceRepository.save(log);
        return toDto(saved);
    }

    /**
     * Maps MaintenanceLog entity to DTO.
     */
    private MaintenanceLogDto toDto(MaintenanceLog log) {
        MaintenanceLogDto dto = new MaintenanceLogDto();
        dto.setLogId(log.getLogId());
        dto.setMaintenanceDate(log.getMaintenanceDate());
        dto.setDescription(log.getDescription());
        dto.setTechnician(log.getTechnician());
        if (log.getDevice() != null) {
            dto.setDeviceId(log.getDevice().getDeviceId());
        }
        return dto;
    }
}
