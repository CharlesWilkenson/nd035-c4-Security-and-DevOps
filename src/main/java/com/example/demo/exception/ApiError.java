package com.example.demo.exception;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiError {
    private int code;
    private String Value;
    private String message;
}
