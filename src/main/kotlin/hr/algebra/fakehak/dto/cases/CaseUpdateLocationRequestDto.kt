package hr.algebra.fakehak.dto.cases

import jakarta.validation.constraints.NotNull

data class CaseUpdateLocationRequestDto(
    @field:NotNull(message = "Latitude is required")
    val latitude: Double,

    @field:NotNull(message = "Longitude is required")
    val longitude: Double
)