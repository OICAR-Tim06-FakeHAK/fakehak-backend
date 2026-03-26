package hr.algebra.fakehak.dto.employee

import hr.algebra.fakehak.enum.EmployeeRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class EmployeeCreateRequestDto(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, message = "Username must be at least 3 characters")
    val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    val password: String,

    @field:NotBlank(message = "First name is required")
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    val lastName: String,

    @field:NotNull(message = "Role is required")
    val role: EmployeeRole = EmployeeRole.DISPATCHER
)