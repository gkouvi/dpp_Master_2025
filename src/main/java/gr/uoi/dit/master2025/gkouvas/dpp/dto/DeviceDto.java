package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import lombok.Data;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

/**
 * Data Transfer Object for Device.
 * Contains only the necessary information exposed over the API.
 */
@Data
public class DeviceDto {

    private Long deviceId;
    private String name;
    private String type;
    private String serialNumber;
    private LocalDate installationDate;
    private String firmwareVersion;
    private String status;
    private String qrBase64;
    private List<MaintenanceInterval> maintenanceIntervals;
    private String buildingName;




    public String getQrBase64() { return qrBase64; }
    public void setQrBase64(String qrBase64) { this.qrBase64 = qrBase64; }


    /**
     * Optional: the building ID this device belongs to.
     */
    private Long buildingId;
    private String ipAddress;   // NEW
    private boolean offline;
    private LocalDate lastMaintenanceDate;


    /**
     * Constructor for converting a Device entity to a DeviceDto.
     * This allows easy mapping inside streams:
     *     devices.stream().map(DeviceDto::new).toList();
     */
    public DeviceDto(Device device) {
        this.deviceId = device.getDeviceId();
        this.name = device.getName();
        this.type = device.getType();
        this.serialNumber = device.getSerialNumber();
        this.installationDate = device.getInstallationDate();
        this.firmwareVersion = device.getFirmwareVersion();
        this.status = device.getStatus();

        this.ipAddress = device.getIpAddress();
        this.offline = device.getOffline();

        this.maintenanceIntervals = device.getMaintenanceIntervals();
        this.lastMaintenanceDate = device.getLastMaintenanceDate();

        // Map buildingId
        this.buildingId = (device.getBuilding() != null)
                ? device.getBuilding().getId()
                : null;

        // QR as Base64
        if (device.getQrCode() != null) {
            this.qrBase64 = Base64.getEncoder().encodeToString(device.getQrCode());
        }
    }


    // Default no-args constructor (needed by Jackson)
    public DeviceDto() {}
}
