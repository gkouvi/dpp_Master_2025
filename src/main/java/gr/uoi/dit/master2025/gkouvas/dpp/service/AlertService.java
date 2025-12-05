package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.AlertDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Alert;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.AlertRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                        "Δεν βρέθηκε συσκευή με id: " + dto.getDeviceId()));

        Alert alert = new Alert();
        alert.setDevice(device);
        alert.setMessage(dto.getMessage());
        alert.setDueDate(dto.getDueDate());
        alert.setStatus(dto.getStatus());
        alert.setCreatedAt(LocalDateTime.now());

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
        dto.setCreatedAt(alert.getCreatedAt());

        if (alert.getDevice() != null) {
            dto.setDeviceId(alert.getDevice().getDeviceId());
            // Στέλνουμε και το όνομα της συσκευής
            dto.setDeviceName(alert.getDevice().getName());
        }

        return dto;
    }

    public AlertDto updateAlert(AlertDto dto) {
        Alert entity = alertRepository.findById(dto.getAlertId())
                .orElseThrow(() -> new RuntimeException("Δεν βρέθηκε ειδοποίηση"));

        entity.setMessage(dto.getMessage());
        entity.setStatus(dto.getStatus());
        entity.setDueDate(dto.getDueDate());
        entity.setDevice(deviceRepository.findById(dto.getDeviceId()).orElse(null));

        return toDto(alertRepository.save(entity));
    }

    public void createOfflineAlert(Device d) {
        Alert a = new Alert();
        a.setDevice(d);
        a.setStatus("OFFLINE");
        a.setMessage("Η συσκευή δεν ανταποκρίθηκε στο ping");
        //a.setDueDate(LocalDate.now());
        a.setCreatedAt(LocalDateTime.now());
        alertRepository.save(a);
    }

    public void createOnlineAlert(Device d) {
        Alert a = new Alert();
        a.setDevice(d);
        a.setStatus("ONLINE");
        a.setMessage("Η συσκευή είναι ξανά προσβάσιμη");
        //a.setDueDate(LocalDate.now());
        a.setCreatedAt(LocalDateTime.now());
alertRepository.save(a);
    }



}
