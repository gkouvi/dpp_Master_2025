package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.EnvironmentalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnvironmentalInfoRepository extends JpaRepository<EnvironmentalInfo, Long> {
    Optional<EnvironmentalInfo> findByDevice_DeviceId(Long deviceId);
}
