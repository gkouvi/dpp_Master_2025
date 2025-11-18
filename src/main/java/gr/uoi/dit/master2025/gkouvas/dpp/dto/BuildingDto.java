package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;

/**
 * Data Transfer Object for Building.
 * Contains building info and parent site ID.
 */
@Data
public class BuildingDto {

    private Long id;
    private String name;
    private String address;
    private Long siteId;  // Parent site
}
