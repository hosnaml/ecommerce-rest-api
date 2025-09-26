// src/main/java/com/hosnaml/store/dtos/ErrorResponse.java
package com.hosnaml.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
