package hr.algebra.fakehak.dto.user

import hr.algebra.fakehak.dto.vehicle.VehicleResponseDto
import hr.algebra.fakehak.enum.AccountStatus
import java.time.LocalDateTime

data class UserResponseDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val accountStatus: AccountStatus,
    val vehicles: List<VehicleResponseDto>,
    val createdAt: LocalDateTime
)