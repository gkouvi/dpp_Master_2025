package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class IotEventDto {

    private String gatewayId;
    private String source;
    private String channel;
    private String state;
    private LocalDateTime ts;
    private Long seq;

    // getters / setters
}

