package com.practice.spring.airbnb.advice;

import org.springframework.http.HttpStatus;
import java.util.List;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    public ApiError(String localizedMessage, HttpStatus unauthorized) {
        //TODO Auto-generated constructor stub
    }
    private HttpStatus status;
    private String message;
    private List<String> subErrors;

    
}
