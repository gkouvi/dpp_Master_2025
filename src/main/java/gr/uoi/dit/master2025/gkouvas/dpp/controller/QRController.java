package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qr")
public class QRController {

    private final BuildingRepository buildingRepo;
    private final DeviceRepository deviceRepo;

    public QRController(BuildingRepository b, DeviceRepository d) {
        this.buildingRepo = b;
        this.deviceRepo = d;
    }

    @GetMapping(value = "/building/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getBuildingQr(@PathVariable Long id) {
        return buildingRepo.findById(id).orElseThrow().getQrCode();
    }

    @GetMapping(value = "/device/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getDeviceQr(@PathVariable Long id) {
        return deviceRepo.findById(id).orElseThrow().getQrCode();
    }
}
