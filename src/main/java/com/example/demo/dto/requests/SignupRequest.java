package com.example.demo.dto.requests;

import com.example.demo.model.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignupRequest {
	@JsonProperty
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<RoleEnum> roleEnum;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
}
