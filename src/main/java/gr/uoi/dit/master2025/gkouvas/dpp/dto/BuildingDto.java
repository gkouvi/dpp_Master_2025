package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import lombok.Data;

import java.util.Base64;

/**
 * Data Transfer Object for Building.
 * Contains building info and parent site ID.
 */
@Data
public class BuildingDto {

    private Long id;
    private String name;
    private String address;
    private Long siteId;
    private String qrBase64;
    private String bimModelRef;
    private String bimFormat;

    public BuildingDto(Building building) {
        this.name = building.getName();
        this.address = building.getAddress();
        this.siteId = building.getSite().getId();
        if (building.getQrCode() != null) {
            this.qrBase64 = Base64.getEncoder().encodeToString(building.getQrCode());
        }
        this.id = id;
        this.bimModelRef = building.getBimModelRef();
        this.bimFormat = building.getBimFormat();
    }

    public BuildingDto() {
    }
}
