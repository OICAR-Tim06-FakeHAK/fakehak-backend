package hr.algebra.fakehak.dto.cases

import jakarta.validation.constraints.NotNull

data class CaseAssignRequestDto(
    @field:NotNull(message = "Employee ID is required")
    val employeeId: Long
)