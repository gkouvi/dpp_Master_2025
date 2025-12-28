package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class TelemetryReading {

    @Id
    @GeneratedValue
    private Long id;

    private Long deviceId;
    private String metric; // TEMP
    private double value;
    private LocalDateTime recordedAt;
}
