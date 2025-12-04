package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.UptimeLog;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.UptimeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceMonitoringService {

    private final DeviceRepository deviceRepo;
    private final AlertService alertService;
    @Autowired
    private UptimeLogRepository uptimeRepo;

    public DeviceMonitoringService(DeviceRepository deviceRepo, AlertService alertService) {
        this.deviceRepo = deviceRepo;
        this.alertService = alertService;
    }



    @Scheduled(cron = "0 */1 * * * *")
    public void monitorDevices() {

        List<Device> devices = deviceRepo.findAll();

        for (Device d : devices) {

            boolean reachable = pingSafe(d.getIpAddress());
            boolean wasOffline = Boolean.TRUE.equals(d.getOffline());

            // -------------------
            // CREATE LOG ENTRY
            // -------------------
            UptimeLog log = new UptimeLog();
            log.setDevice(d);
            log.setTimestamp(LocalDateTime.now());
            log.setOnline(reachable);
            uptimeRepo.save(log);

            // -------------------
            // ALERT LOGIC
            // -------------------
            if (!reachable && !wasOffline) {
                alertService.createOfflineAlert(d);
                d.setOffline(true);
            }
            else if (reachable && wasOffline) {
                alertService.createOnlineAlert(d);
                d.setOffline(false);
            }

            d.setLastCheck(LocalDateTime.now());
            deviceRepo.save(d);
        }
    }


    /*private boolean pingSafe(String ip) {
        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 -W 1 " + ip);
            return p.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }*/
    public double calculateUptime(Long deviceId) {
        LocalDateTime from = LocalDateTime.now().minusDays(30);

        List<UptimeLog> logs = uptimeRepo.getLogsForDevice(deviceId, from);

        if (logs.isEmpty()) return 100.0;

        long total = logs.size();
        long online = logs.stream().filter(UptimeLog::isOnline).count();

        return (online * 100.0) / total;
    }



    private boolean pingSafe(String ip) {
        try {
            InetAddress inet = InetAddress.getByName(ip);
            return inet.isReachable(2000); // timeout 2 sec
        } catch (Exception e) {
            return false;
        }
    }
}
