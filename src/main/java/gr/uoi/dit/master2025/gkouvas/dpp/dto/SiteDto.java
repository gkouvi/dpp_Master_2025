package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;
import java.util.List;

/**
 * Data Transfer Object for DppSite.
 * Contains site info and optionally the list of building IDs.
 */
@Data
public class SiteDto {
    private Long id;
    private String name;
    private String region;
    private String coordinates;

    /** List of building IDs belonging to the site */
    private List<Long> buildingIds;
}

