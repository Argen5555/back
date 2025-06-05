package com.example.carservice.dto.request;

import com.example.carservice.model.ServiceModel;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequestDto {
    private Boolean diagnostic;
    private Long orderId;
    private Long masterId;
    private BigDecimal price;
    private ServiceModel.ServiceStatus status;
}
