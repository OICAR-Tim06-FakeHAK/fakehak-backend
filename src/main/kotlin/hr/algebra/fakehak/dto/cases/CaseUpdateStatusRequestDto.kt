package hr.algebra.fakehak.dto.cases

import hr.algebra.fakehak.enum.CaseStatus
import jakarta.validation.constraints.NotNull

data class CaseUpdateStatusRequestDto(
    @field:NotNull(message = "Status is required")
    val status: CaseStatus
)