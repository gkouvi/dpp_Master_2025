package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import lombok.Data;

import java.time.LocalDate;

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

    public String getQrBase64() { return qrBase64; }
    public void setQrBase64(String qrBase64) { this.qrBase64 = qrBase64; }


    /**
     * Optional: the building ID this device belongs to.
     */
    private Long buildingId;


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

        // Building ID mapping (null-safe)
        this.buildingId = (device.getBuilding() != null)
                ? device.getBuilding().getId()
                : null;
    }


    // Default no-args constructor (needed by Jackson)
    public DeviceDto() {}
}
