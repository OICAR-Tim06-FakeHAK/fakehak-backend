package hr.algebra.fakehak.dto.note

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class NoteCreateRequestDto(
    @field:NotBlank(message = "Note content is required")
    val content: String,

    @field:NotNull(message = "Employee ID is required")
    val employeeId: Long
)