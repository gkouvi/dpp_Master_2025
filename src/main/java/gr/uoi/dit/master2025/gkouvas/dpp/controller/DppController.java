package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dpp")
public class DppController {

    private final BuildingRepository buildingRepo;
    private final DeviceRepository deviceRepo;

    public DppController(BuildingRepository b, DeviceRepository d) {
        this.buildingRepo = b;
        this.deviceRepo = d;
    }

    @GetMapping(value = "/building/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String buildingPage(@PathVariable Long id) {
        Building b = buildingRepo.findById(id).orElseThrow();
        return """
            <html><body>
            <h2>Building: %s</h2>
            <p>ID: %d</p>
            </body></html>
        """.formatted(b.getName(), b.getId());
    }

    @GetMapping(value = "/device/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String devicePage(@PathVariable Long id) {
        Device d = deviceRepo.findById(id).orElseThrow();
        return """
            <html><body>
            <h2>Device: %s</h2>
            <p>Serial: %s</p>
            </body></html>
        """.formatted(d.getName(), d.getSerialNumber());
    }
}

