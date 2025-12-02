package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.EnvironmentalInfoDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.EnvironmentalInfo;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.EnvironmentalInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentalInfoService {

    private final EnvironmentalInfoRepository repo;
    private final DeviceRepository deviceRepo;

    public EnvironmentalInfoService(EnvironmentalInfoRepository repo, DeviceRepository deviceRepo) {
        this.repo = repo;
        this.deviceRepo = deviceRepo;
    }

    public EnvironmentalInfoDto getByDeviceId(Long deviceId) {
        EnvironmentalInfo env = repo.findByDevice_DeviceId(deviceId)
                .orElse(null);

        if (env == null) return null;

        EnvironmentalInfoDto dto = new EnvironmentalInfoDto();
        dto.setId(env.getId());
        dto.setDeviceId(deviceId);
        dto.setMaterialsComposition(env.getMaterialsComposition());
        dto.setRecyclingInstructions(env.getRecyclingInstructions());
        dto.setHazardousMaterials(env.getHazardousMaterials());
        dto.setRecyclabilityPercentage(env.getRecyclabilityPercentage());
        dto.setDeviceWeightKg(env.getDeviceWeightKg());

        return dto;
    }

    public EnvironmentalInfoDto save(EnvironmentalInfoDto dto) {
        Device device = deviceRepo.findById(dto.getDeviceId())
                .orElseThrow();

        EnvironmentalInfo env = repo.findByDevice_DeviceId(dto.getDeviceId())
                .orElse(new EnvironmentalInfo());

        env.setDevice(device);
        env.setMaterialsComposition(dto.getMaterialsComposition());
        env.setRecyclingInstructions(dto.getRecyclingInstructions());
        env.setHazardousMaterials(dto.getHazardousMaterials());
        env.setRecyclabilityPercentage(dto.getRecyclabilityPercentage());
        env.setDeviceWeightKg(dto.getDeviceWeightKg());

        repo.save(env);
        dto.setId(env.getId());
        return dto;
    }
}
