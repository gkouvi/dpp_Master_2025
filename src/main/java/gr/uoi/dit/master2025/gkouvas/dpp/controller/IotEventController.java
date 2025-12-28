package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.IotEventDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.IotEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/iot")
public class IotEventController {

    private final IotEventService service;

    public IotEventController(IotEventService service) {
        this.service = service;
    }

    @PostMapping("/events")
    public ResponseEntity<?> receiveEvent(@RequestBody IotEventDto dto) {
        service.handleEvent(dto);
        return ResponseEntity.ok().build();
    }
}

