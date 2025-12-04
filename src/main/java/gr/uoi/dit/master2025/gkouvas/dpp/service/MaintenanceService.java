package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceLogDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.MaintenanceLog;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.MaintenanceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.util.MaintenanceStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service λογικής για Maintenance Logs (Device + Building).
 */
@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final DeviceRepository deviceRepository;
    private final BuildingRepository buildingRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              DeviceRepository deviceRepository,
                              BuildingRepository buildingRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.deviceRepository = deviceRepository;
        this.buildingRepository = buildingRepository;
    }

    // ------------------- READ -------------------

    /** Επιστρέφει όλα τα logs (για dashboard κλπ). */
    public List<MaintenanceLogDto> getAllLogs() {
        return maintenanceRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** Επιστρέφει logs για συγκεκριμένη συσκευή. */
    public List<MaintenanceLogDto> getMaintenanceForDevice(Long deviceId) {
        return maintenanceRepository.findByDevice_DeviceId(deviceId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** Επιστρέφει logs για συγκεκριμένο κτήριο. */
    public List<MaintenanceLogDto> getMaintenanceForBuilding(Long buildingId) {
        return maintenanceRepository.findByBuilding_Id(buildingId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ------------------- CREATE -------------------

    /**
     * Δημιουργεί νέο maintenance log.
     * Μπορεί να αφορά:
     *  - μόνο συσκευή (deviceId != null)
     *  - μόνο κτήριο (buildingId != null)
     *  - ή και τα δύο.
     */
    public MaintenanceLogDto createLog(MaintenanceLogDto dto) {

        MaintenanceLog log = toEntity(dto);

        // -------- Συσκευή ----------
        if (dto.getDeviceId() != null) {
            Device device = deviceRepository.findById(dto.getDeviceId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Δεν βρέθηκε συσκευή: " + dto.getDeviceId()
                    ));

            log.setDevice(device);  // <-- ΣΩΣΤΟ
        }

        // -------- Κτίριο ----------
        if (dto.getBuildingId() != null) {
            Building building = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Το κτίριο δεν βρέθηκε: " + dto.getBuildingId()
                    ));
            log.setBuilding(building);
        }

        // -------- Λογική κατάστασης --------
        if (log.getPerformedDate() == null && log.getPlannedDate() == null) {
            log.setPerformedDate(LocalDate.now());
            log.setStatus(MaintenanceStatus.COMPLETED);
        } else if (log.getStatus() == null) {
            log.setStatus(MaintenanceStatus.PLANNED);
        }

        MaintenanceLog saved = maintenanceRepository.save(log);
        return toDto(saved);
    }

    public MaintenanceLogDto complete(Long id) {
        MaintenanceLog m = maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Δεν βρέθηκε συντήρησηd: " + id));

        m.setPerformedDate(LocalDate.now());
        m.setStatus(MaintenanceStatus.COMPLETED);

        MaintenanceLog saved = maintenanceRepository.save(m);
        return toDto(saved);
    }

    // ------------------- MAPPING -------------------

    private MaintenanceLogDto toDto(MaintenanceLog log) {
        MaintenanceLogDto dto = new MaintenanceLogDto();
        dto.setLogId(log.getLogId());
        dto.setMaintenanceDate(log.getMaintenanceDate());
        dto.setDescription(log.getDescription());
        dto.setTechnician(log.getTechnician());
        dto.setPlannedDate(log.getPlannedDate());
        dto.setPerformedDate(log.getPerformedDate());
        dto.setInterval(log.getInterval());
        dto.setStatus(log.getStatus());

        if (log.getDevice() != null) {
            dto.setDeviceId(log.getDevice().getDeviceId());
        }

        if (log.getBuilding() != null) {
            dto.setBuildingId(log.getBuilding().getId());
        }

        return dto;
    }

    public MaintenanceLog toEntity(MaintenanceLogDto dto) {
        MaintenanceLog m = new MaintenanceLog();
        m.setLogId(dto.getLogId());
        m.setPlannedDate(dto.getPlannedDate());
        m.setPerformedDate(dto.getPerformedDate());
        m.setInterval(dto.getInterval());
        m.setDescription(dto.getDescription());
        m.setTechnician(dto.getTechnician());
        m.setStatus(dto.getStatus());

        // Device/Building μπαίνουν στο service.
        return m;
    }

    public MaintenanceLogDto update(MaintenanceLogDto dto) {
        MaintenanceLog existing = maintenanceRepository.findById(dto.getLogId())
                .orElseThrow(() -> new IllegalArgumentException("Δεν βρέθηκε συντήρηση: " + dto.getLogId()));

        // ενημέρωση πεδίων
        existing.setDescription(dto.getDescription());
        existing.setTechnician(dto.getTechnician());
        existing.setMaintenanceDate(dto.getMaintenanceDate());
        existing.setPlannedDate(dto.getPlannedDate());
        existing.setPerformedDate(dto.getPerformedDate());
        existing.setInterval(dto.getInterval());
        existing.setStatus(dto.getStatus());

        // device / building αν επιτρέπεις αλλαγή:
        if (dto.getDeviceId() != null) {
            Device device = deviceRepository.findById(dto.getDeviceId())
                    .orElseThrow(() -> new IllegalArgumentException("Δεν βρέθηκε συσκευή: " + dto.getDeviceId()));
            existing.setDevice(device); // ΠΡΟΣΟΧΗ: όχι setDeviceId αν το entity έχει Device
        }

        if (dto.getBuildingId() != null) {
            Building building = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow(() -> new IllegalArgumentException("Το κτίριο δεν βρέθηκε: " + dto.getBuildingId()));
            existing.setBuilding(building);
        }

        MaintenanceLog saved = maintenanceRepository.save(existing);
        return toDto(saved);
    }

    public Map<String, Long> getUpcomingByMonth() {
        List<Object[]> raw = maintenanceRepository.getUpcomingByMonthNative();

        Map<String, Long> result = new LinkedHashMap<>();

        for (Object[] row : raw) {

            String month = (String) row[0];
            Long count = ((Number) row[1]).longValue();


            result.put(month, count);
        }

        return result;
    }


}
