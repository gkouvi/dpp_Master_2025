package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.IotEventDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.IotProperties;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Alert;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.IotEvent;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.AlertRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.IotEventRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.util.AlertSeverity;
import gr.uoi.dit.master2025.gkouvas.dpp.util.AlertStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IotEventService {

    private final IotEventRepository iotRepo;
    private final AlertRepository alertRepo;
    private final DeviceRepository deviceRepo;
    private final IotProperties props;

    public IotEventService(
            IotEventRepository iotRepo,
            AlertRepository alertRepo,
            DeviceRepository deviceRepo,
            IotProperties props
    ) {
        this.iotRepo = iotRepo;
        this.alertRepo = alertRepo;
        this.deviceRepo = deviceRepo;
        this.props = props;
    }

    public void handleEvent(IotEventDto dto) {

        // 1️ save IoT event
        IotEvent e = new IotEvent();
        e.setGatewayId(dto.getGatewayId());
        e.setSource(dto.getSource());
        e.setChannel(dto.getChannel());
        e.setState(dto.getState());
        e.setSeq(dto.getSeq());
        e.setEventTs(dto.getTs() != null ? dto.getTs() : LocalDateTime.now());
        e.setReceivedAt(LocalDateTime.now());
        iotRepo.save(e);

        // 2️ create alert if needed
        if (isAlertCondition(dto)) {
            createAlertFromIot(dto);
        }
    }

    private boolean isAlertCondition(IotEventDto dto) {
        return "ON".equalsIgnoreCase(dto.getState())
                || "TRIGGERED".equalsIgnoreCase(dto.getState());
    }

    private void createAlertFromIot(IotEventDto dto) {

        Alert alert = new Alert();
        alert.setMessage(
                "IoT alarm from " + dto.getGatewayId() +
                        " (" + dto.getChannel() + ")"
        );

        alert.setSeverity(AlertSeverity.MEDIUM);
        alert.setStatus(AlertStatus.OPEN);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setDueDate(LocalDateTime.now().plusHours(24));

        // 🔗 HERE IS THE MAGIC
        Long deviceId = props.getGatewayDeviceMap().get(dto.getGatewayId());
        if (deviceId != null) {
            deviceRepo.findById(deviceId).ifPresent(alert::setDevice);
        }

        alertRepo.save(alert);
    }
}
