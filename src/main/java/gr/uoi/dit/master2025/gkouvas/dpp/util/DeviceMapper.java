package gr.uoi.dit.master2025.gkouvas.dpp.util;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DeviceDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public DeviceDto toDto(Device d) {
        DeviceDto dto = new DeviceDto();
        dto.setDeviceId(d.getDeviceId());
        dto.setName(d.getName());
        dto.setType(d.getType());
        dto.setSerialNumber(d.getSerialNumber());
        dto.setInstallationDate(d.getInstallationDate());
        dto.setFirmwareVersion(d.getFirmwareVersion());
        dto.setStatus(d.getStatus());
        dto.setIpAddress(d.getIpAddress());   // NEW
        dto.setOffline(d.getOffline());        // NEW

        if (d.getBuilding() != null)
            dto.setBuildingId(d.getBuilding().getId());

        return dto;
    }

    public void updateEntity(Device d, DeviceDto dto, Building building) {
        d.setName(dto.getName());
        d.setType(dto.getType());
        d.setSerialNumber(dto.getSerialNumber());
        d.setInstallationDate(dto.getInstallationDate());
        d.setFirmwareVersion(dto.getFirmwareVersion());
        d.setStatus(dto.getStatus());
        d.setIpAddress(dto.getIpAddress());  // NEW
        d.setOffline(dto.isOffline());       // NEW
        d.setBuilding(building);
    }
}

