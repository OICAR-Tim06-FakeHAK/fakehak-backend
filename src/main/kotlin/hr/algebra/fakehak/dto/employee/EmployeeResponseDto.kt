package hr.algebra.fakehak.dto.employee

import hr.algebra.fakehak.enum.EmployeeRole
import java.time.LocalDateTime

data class EmployeeResponseDto(
    val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val role: EmployeeRole,
    val active: Boolean,
    val createdAt: LocalDateTime
)
