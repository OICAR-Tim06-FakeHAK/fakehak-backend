package hr.algebra.fakehak.dto.cases

import hr.algebra.fakehak.dto.employee.EmployeeResponseDto
import hr.algebra.fakehak.dto.note.NoteResponseDto
import hr.algebra.fakehak.dto.user.UserSummaryResponseDto
import hr.algebra.fakehak.dto.vehicle.VehicleResponseDto
import hr.algebra.fakehak.enum.CaseStatus
import java.time.LocalDateTime

data class CaseResponseDto(
    val id: Long,
    val user: UserSummaryResponseDto,
    val vehicle: VehicleResponseDto,
    val latitude: Double,
    val longitude: Double,
    val description: String?,
    val status: CaseStatus,
    val assignedEmployee: EmployeeResponseDto?,
    val notes: List<NoteResponseDto>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)