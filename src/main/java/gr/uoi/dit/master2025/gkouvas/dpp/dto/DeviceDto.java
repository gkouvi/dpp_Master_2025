package gr.uoi.dit.master2025.gkouvas.dpp.dto;

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

    /**
     * Optional: the building ID this device belongs to.
     * Can be null if the device is not yet assigned to a building.
     */
    private Long buildingId;
}

