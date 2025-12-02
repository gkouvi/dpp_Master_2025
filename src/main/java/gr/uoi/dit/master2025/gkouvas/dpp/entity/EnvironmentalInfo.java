package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "environmental_info")
public class EnvironmentalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "device_id", nullable = false, unique = true)
    private Device device;

    @Column(columnDefinition = "TEXT")
    private String materialsComposition;

    @Column(columnDefinition = "TEXT")
    private String recyclingInstructions;

    @Column(columnDefinition = "TEXT")
    private String hazardousMaterials;

    private Double recyclabilityPercentage;

    private Double deviceWeightKg;

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }

    public String getMaterialsComposition() { return materialsComposition; }
    public void setMaterialsComposition(String materialsComposition) { this.materialsComposition = materialsComposition; }

    public String getRecyclingInstructions() { return recyclingInstructions; }
    public void setRecyclingInstructions(String recyclingInstructions) { this.recyclingInstructions = recyclingInstructions; }

    public String getHazardousMaterials() { return hazardousMaterials; }
    public void setHazardousMaterials(String hazardousMaterials) { this.hazardousMaterials = hazardousMaterials; }

    public Double getRecyclabilityPercentage() { return recyclabilityPercentage; }
    public void setRecyclabilityPercentage(Double recyclabilityPercentage) { this.recyclabilityPercentage = recyclabilityPercentage; }

    public Double getDeviceWeightKg() { return deviceWeightKg; }
    public void setDeviceWeightKg(Double deviceWeightKg) { this.deviceWeightKg = deviceWeightKg; }
}
