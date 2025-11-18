package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a military facility site (e.g., camp, base, building complex)
 * within the Digital Product Passport system.
 *
 * A DppSite contains multiple Buildings.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DppSite {

    /** Primary key of the site */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Human-readable site name */
    private String name;

    /** Region or area where the site belongs (prefecture, military sector, etc.) */
    private String region;

    /** Optional GPS coordinates */
    private String coordinates;

    /**
     * One-to-many relationship with buildings.
     * A site contains multiple buildings.
     *
     * mappedBy = "site" → the foreign key is in Building.site
     * LAZY loading → buildings load only when needed
     */
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("site-buildings")
    private List<Building> buildings;
}
