package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a building within a military site.
 *
 * Each building belongs to exactly one DppSite
 * and contains multiple devices (e.g., cameras, access control, alarms).
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    /** Primary key of the building */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the building (e.g., HQ, Warehouse 1, Barracks A) */
    private String name;

    /** Optional address or internal facility code */
    private String address;

    /**
     * Many buildings belong to one DppSite.
     * This is the owning side of the relationship.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    @JsonBackReference("site-buildings")
    private DppSite site;

    /**
     * One-to-many relationship with devices.
     * A building contains multiple devices.
     */
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("building-devices")
    private List<Device> devices;


    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] qrCode;

}
