package hr.algebra.fakehak.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UserUpdateRequestDto(
    @field:Size(min = 1, message = "First name must not be empty")
    val firstName: String? = null,

    @field:Size(min = 1, message = "Last name must not be empty")
    val lastName: String? = null,

    @field:Size(min = 1, message = "Phone number must not be empty")
    val phoneNumber: String? = null,

    @field:Email(message = "Invalid email format")
    val email: String? = null
)