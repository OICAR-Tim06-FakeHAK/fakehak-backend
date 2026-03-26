package hr.algebra.fakehak.dto.login

import jakarta.validation.constraints.NotBlank

data class LoginRequestDto(
    @field:NotBlank(message = "Email or username is required")
    val identifier: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)