package com.sifat.MyCRM.dto.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseModelDTO {
    private String status;
    private String message;
    private String path;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private Object data;
}
