package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceHealthDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.OverallHealthDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.UptimeLog;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.UptimeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthService {

    @Autowired
    private DeviceRepository deviceRepo;
    @Autowired private UptimeLogRepository uptimeRepo;

    public OverallHealthDto getFleetHealth() {

        List<Device> devices = deviceRepo.findAll();

        OverallHealthDto dto = new OverallHealthDto();
        dto.totalDevices = devices.size();

        dto.devices = devices.stream().map(d -> {

            DeviceHealthDto h = new DeviceHealthDto();
            h.deviceId = d.getDeviceId();
            h.name = d.getName();
            h.online = !Boolean.TRUE.equals(d.getOffline());
            h.lastCheck = d.getLastCheck();
            h.uptimePercent = calculateUptime(d.getDeviceId());
            return h;

        }).toList();

        dto.onlineCount =
                dto.devices.stream().filter(h -> h.online).count();

        dto.offlineCount = dto.totalDevices - dto.onlineCount;

        dto.fleetUptimePercent =
                dto.devices.stream()
                        .mapToDouble(h -> h.uptimePercent)
                        .average()
                        .orElse(0);

        return dto;
    }

    private double calculateUptime(Long deviceId) {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        List<UptimeLog> logs = uptimeRepo.getLogsForDevice(deviceId, from);

        if (logs.isEmpty()) return 100;

        long total = logs.size();
        long online = logs.stream().filter(UptimeLog::isOnline).count();
        return (online * 100.0) / total;
    }
}

