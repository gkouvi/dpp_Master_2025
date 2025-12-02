package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.EnvironmentalInfoDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.EnvironmentalInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/environment")
public class EnvironmentalInfoController {

    private final EnvironmentalInfoService service;

    public EnvironmentalInfoController(EnvironmentalInfoService service) {
        this.service = service;
    }

    /*@GetMapping("/{deviceId}")
    public ResponseEntity<EnvironmentalInfoDto> get(@PathVariable Long deviceId) {
        return ResponseEntity.ok(service.getByDeviceId(deviceId));
    }*/
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<EnvironmentalInfoDto> get(@PathVariable Long deviceId) {
        return ResponseEntity.ok(service.getByDeviceId(deviceId));
    }


    @PostMapping
    public ResponseEntity<EnvironmentalInfoDto> save(@RequestBody EnvironmentalInfoDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }
}
