package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.AlertDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Alert;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.AlertRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing Alert entities and business logic.
 */
@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final DeviceRepository deviceRepository;

    public AlertService(AlertRepository alertRepository, DeviceRepository deviceRepository) {
        this.alertRepository = alertRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * Retrieves all alerts.
     *
     * @return list of AlertDto
     */
    public List<AlertDto> getAllAlerts() {
        return alertRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves alerts for a specific device.
     *
     * @param deviceId the device ID
     * @return list of AlertDto
     */
    public List<AlertDto> getAlertsByDevice(Long deviceId) {
        return alertRepository.findByDevice_DeviceId(deviceId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new alert for a device.
     *
     * @param dto DTO containing alert data
     * @return saved AlertDto
     */
    public AlertDto createAlert(AlertDto dto) {
        Device device = deviceRepository.findById(dto.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Device not found with id: " + dto.getDeviceId()));

        Alert alert = new Alert();
        alert.setDevice(device);
        alert.setMessage(dto.getMessage());
        alert.setDueDate(dto.getDueDate());
        alert.setStatus(dto.getStatus());

        Alert saved = alertRepository.save(alert);
        return toDto(saved);
    }

    /**
     * Maps Alert entity to AlertDto.
     */
    private AlertDto toDto(Alert alert) {
        AlertDto dto = new AlertDto();
        dto.setAlertId(alert.getAlertId());
        dto.setMessage(alert.getMessage());
        dto.setDueDate(alert.getDueDate());
        dto.setStatus(alert.getStatus());
        if (alert.getDevice() != null) {
            dto.setDeviceId(alert.getDevice().getDeviceId());
        }
        return dto;
    }
}
