package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "iot_event")
public class IotEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gateway_id", nullable = false, length = 64)
    private String gatewayId;

    @Column(nullable = false, length = 32)
    private String source;

    @Column(nullable = false, length = 32)
    private String channel;

    @Column(nullable = false, length = 32)
    private String state;

    @Column(name = "event_ts", nullable = false)
    private LocalDateTime eventTs;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    private Long seq;

    // getters / setters
}

