package gr.uoi.dit.master2025.gkouvas.dpp.dto;

public class EnvironmentalInfoDto {

    private Long id;
    private Long deviceId;
    private String materialsComposition;
    private String recyclingInstructions;
    private String hazardousMaterials;
    private Double recyclabilityPercentage;
    private Double deviceWeightKg;

    // GETTERS & SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getMaterialsComposition() {
        return materialsComposition;
    }

    public void setMaterialsComposition(String materialsComposition) {
        this.materialsComposition = materialsComposition;
    }

    public String getRecyclingInstructions() {
        return recyclingInstructions;
    }

    public void setRecyclingInstructions(String recyclingInstructions) {
        this.recyclingInstructions = recyclingInstructions;
    }

    public String getHazardousMaterials() {
        return hazardousMaterials;
    }

    public void setHazardousMaterials(String hazardousMaterials) {
        this.hazardousMaterials = hazardousMaterials;
    }

    public Double getRecyclabilityPercentage() {
        return recyclabilityPercentage;
    }

    public void setRecyclabilityPercentage(Double recyclabilityPercentage) {
        this.recyclabilityPercentage = recyclabilityPercentage;
    }

    public Double getDeviceWeightKg() {
        return deviceWeightKg;
    }

    public void setDeviceWeightKg(Double deviceWeightKg) {
        this.deviceWeightKg = deviceWeightKg;
    }
}
