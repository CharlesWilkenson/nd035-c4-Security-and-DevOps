package com.example.demo.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class JwtResponse {
    private String token;
    private Long id;
    private String type = "Bearer";
    private String username;
    private String email;
    private List<String> roleEnums;
}
