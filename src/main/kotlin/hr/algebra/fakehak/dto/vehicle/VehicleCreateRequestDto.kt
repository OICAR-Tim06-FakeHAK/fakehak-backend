package hr.algebra.fakehak.dto.vehicle

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class VehicleCreateRequestDto(
    @field:NotBlank(message = "Brand is required")
    val brand: String,

    @field:NotBlank(message = "Model is required")
    val model: String,

    @field:NotBlank(message = "VIN is required")
    @field:Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    val vin: String,

    @field:NotBlank(message = "Registration plate is required")
    val registrationPlate: String,

    @field:NotNull(message = "First registration date is required")
    val firstRegistrationDate: LocalDate
)