package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.FailureHeatCell;
import gr.uoi.dit.master2025.gkouvas.dpp.service.AlertHeatmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class HeatmapController {

    @Autowired
    private AlertHeatmapService service;

    @GetMapping("/heatmap")
    public List<FailureHeatCell> getHeatmap() {
        return service.getHeatmap();
    }
}
