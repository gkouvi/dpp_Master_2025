/*
package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceInterval;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceLogDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.MaintenanceLog;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

*/
/**
 * Service layer for managing Device entities.
 *//*

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final BuildingRepository buildingRepository;

    @Autowired
    private MaintenanceService maintenanceService;


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
        d.setIpAddress(dto.getIpAddress());
        d.setMaintenanceInterval(dto.getMaintenanceInterval());



        Device saved = deviceRepository.save(d);
        MaintenanceLogDto log = new MaintenanceLogDto();
        log.setDeviceId(saved.getDeviceId());
        log.setDescription("Αρχική εγγραφή συσκευής");
        log.setTechnician("ΣΥΣΤΗΜΑ");
        log.setMaintenanceDate(LocalDate.now());

        maintenanceService.createLog(log);

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

    */
/*public DeviceDto updateDevice(Long id, DeviceDto dto) {

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
    }*//*
//27112025

    public DeviceDto updateDevice(Long id, DeviceDto dto) {

        Device d = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Δεν βρέθηκε συσκευή: " + id));

        d.setName(dto.getName());
        d.setType(dto.getType());
        d.setSerialNumber(dto.getSerialNumber());
        d.setFirmwareVersion(dto.getFirmwareVersion());
        d.setInstallationDate(dto.getInstallationDate());
        d.setStatus(dto.getStatus());

        // NEW: update building
        if (dto.getBuildingId() != null) {
            Building b = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow();
            d.setBuilding(b);
        }

        // NEW: update IP address
        d.setIpAddress(dto.getIpAddress());

        // NEW: update maintenance interval
        if (dto.getMaintenanceInterval() != null) {
            d.setMaintenanceInterval(dto.getMaintenanceInterval());
        }

        // NEW: offline state (used by ping monitor)
        d.setOffline(dto.isOffline());

        // optional – if user edits it
        if (dto.getLastMaintenanceDate() != null) {
            d.setLastMaintenanceDate(dto.getLastMaintenanceDate());
        }

        Device saved = deviceRepository.save(d);

        return new DeviceDto(saved);
    }


    private LocalDate getNextMaintenance(LocalDate last, MaintenanceInterval interval) {
        if (last == null) {
            last = LocalDate.now();
        }
        return switch (interval) {
            case DAILY       -> last.plusDays(1);
            case MONTHLY     -> last.plusMonths(1);
            case SEMI_ANNUAL -> last.plusMonths(6);
            case ANNUAL      -> last.plusYears(1);
        };
    }


    public List<DeviceDto> getUpcomingMaintenance() {

        LocalDate today  = LocalDate.now();
        LocalDate limit  = today.plusDays(30);

        return deviceRepository.findAll().stream()
                .filter(d -> {
                    if (d.getMaintenanceInterval() == null) return false;

                    LocalDate last = d.getLastMaintenanceDate();
                    if (last == null) last = d.getInstallationDate();
                    if (last == null) last = today;


                    LocalDate next = getNextMaintenance(last, d.getMaintenanceInterval());
                    return !next.isBefore(today) && !next.isAfter(limit);
                })
                .map(this::toDto)
                .toList();
    }







}
*/
package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.*;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing Device entities.
 */
@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final BuildingRepository buildingRepository;

    @Autowired
    private MaintenanceService maintenanceService;

    public DeviceService(DeviceRepository deviceRepository,
                         BuildingRepository buildingRepository) {
        this.deviceRepository = deviceRepository;
        this.buildingRepository = buildingRepository;
    }

    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll()
                .stream()
                .map(DeviceDto::new)
                .toList();
    }

    public DeviceDto getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Δεν βρέθηκε συσκευή: " + id));
        return new DeviceDto(device);
    }

    public DeviceDto createDevice(DeviceDto dto) {

        Device d = new Device();
        d.setName(dto.getName());
        d.setType(dto.getType());
        d.setSerialNumber(dto.getSerialNumber());
        d.setInstallationDate(dto.getInstallationDate());
        d.setFirmwareVersion(dto.getFirmwareVersion());
        d.setStatus(dto.getStatus());
        d.setIpAddress(dto.getIpAddress());

       // NEW - MULTIPLE INTERVALS
        if (dto.getMaintenanceIntervals() != null) {
            d.setMaintenanceIntervals(dto.getMaintenanceIntervals());
        }
        d.setLastMaintenanceDate(LocalDate.now());

        Building b = buildingRepository.findById(dto.getBuildingId())
                .orElseThrow();
        d.setBuilding(b);

        Device saved = deviceRepository.save(d);

        // initial maintenance log
        MaintenanceLogDto log = new MaintenanceLogDto();
        log.setDeviceId(saved.getDeviceId());
        log.setDescription("Αρχική εγγραφή συσκευής");
        log.setTechnician("ΣΥΣΤΗΜΑ");
        log.setMaintenanceDate(LocalDate.now());
        maintenanceService.createLog(log);

        return new DeviceDto(saved);
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

    @Transactional
    public DeviceDto updateDevice(Long id, DeviceDto dto) {

        Device d = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Δεν βρέθηκε συσκευή: " + id));

        d.setName(dto.getName());
        d.setType(dto.getType());
        d.setSerialNumber(dto.getSerialNumber());
        d.setFirmwareVersion(dto.getFirmwareVersion());
        d.setInstallationDate(dto.getInstallationDate());
        d.setStatus(dto.getStatus());
        d.setIpAddress(dto.getIpAddress());
        d.setOffline(dto.isOffline());

        if (dto.getLastMaintenanceDate() != null) {
            d.setLastMaintenanceDate(dto.getLastMaintenanceDate());
        }

        if (dto.getBuildingId() != null) {
            Building b = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow();
            d.setBuilding(b);
        }

        // NEW - MULTIPLE INTERVALS
        if (dto.getMaintenanceIntervals() != null) {
            d.setMaintenanceIntervals(dto.getMaintenanceIntervals());
        }

        Device saved = deviceRepository.save(d);
        return new DeviceDto(saved);
    }


    private LocalDate getNextMaintenance(LocalDate last, MaintenanceInterval interval) {
        if (last == null) last = LocalDate.now();

        return switch (interval) {
            case DAILY       -> last.plusDays(1);
            case MONTHLY     -> last.plusMonths(1);
            case SEMI_ANNUAL -> last.plusMonths(6);
            case ANNUAL      -> last.plusYears(1);
        };
    }

    public List<DeviceDto> getUpcomingMaintenance() {

        LocalDate today  = LocalDate.now();
        LocalDate limit  = today.plusDays(30);

        return deviceRepository.findAll().stream()
                .filter(d -> {

                    // ignore devices with no intervals selected
                    if (d.getMaintenanceIntervals() == null
                            || d.getMaintenanceIntervals().isEmpty()) {
                        return false;
                    }

                    // find last maintenance baseline
                    LocalDate last = d.getLastMaintenanceDate();
                    if (last == null) last = d.getInstallationDate();
                    if (last == null) last = today;

                    // check ALL selected intervals
                    for (MaintenanceInterval interval : d.getMaintenanceIntervals()) {

                        LocalDate next = getNextMaintenance(last, interval);

                        if (!next.isBefore(today) && !next.isAfter(limit)) {
                            return true;   // FOUND A MATCH → include device
                        }
                    }

                    return false;
                })
                .map(DeviceDto::new)
                .toList();
    }
    public List<UpcomingMaintenanceItemDto> getUpcomingMaintenanceDetails() {

        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(30);

        List<UpcomingMaintenanceItemDto> result = new ArrayList<>();

        for (Device d : deviceRepository.findAll()) {

            if (d.getMaintenanceIntervals() == null || d.getMaintenanceIntervals().isEmpty())
                continue;

            LocalDate last = d.getLastMaintenanceDate();
            if (last == null) last = d.getInstallationDate();
            if (last == null) last = today;

            for (MaintenanceInterval interval : d.getMaintenanceIntervals()) {

                LocalDate next = getNextMaintenance(last, interval);

                if (!next.isBefore(today) && !next.isAfter(limit)) {
                    result.add(new UpcomingMaintenanceItemDto(
                            d.getDeviceId(),
                            d.getName(),
                            interval,
                            next
                    ));
                }
            }
        }

        return result;
    }

    public MaintenanceKpiDto getMaintenanceKpis() {

        LocalDate today = LocalDate.now();
        MaintenanceKpiDto dto = new MaintenanceKpiDto();

        for (Device d : deviceRepository.findAll()) {

            if (d.getMaintenanceIntervals() == null || d.getMaintenanceIntervals().isEmpty())
                continue;

            LocalDate last = d.getLastMaintenanceDate();
            if (last == null) last = d.getInstallationDate();
            if (last == null) last = today;

            for (MaintenanceInterval interval : d.getMaintenanceIntervals()) {

                LocalDate next = getNextMaintenance(last, interval);
                long days = ChronoUnit.DAYS.between(today, next);

                if (next.isBefore(today)) {
                    dto.setOverdue(dto.getOverdue() + 1);
                } else if (days <= 3) {
                    dto.setCritical(dto.getCritical() + 1);
                } else if (days <= 7) {
                    dto.setUrgent(dto.getUrgent() + 1);
                }

                if (next.getMonth() == today.getMonth() &&
                        next.getYear() == today.getYear()) {
                    dto.setThisMonth(dto.getThisMonth() + 1);
                }
            }
        }

        return dto;
    }


}
