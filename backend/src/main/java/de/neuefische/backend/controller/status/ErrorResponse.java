package de.neuefische.backend.controller.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class ErrorResponse {

    private String errorMassage;
    private LocalDateTime timestamp;
    private String requestUri;

}
