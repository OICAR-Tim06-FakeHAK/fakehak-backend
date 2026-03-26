package hr.algebra.fakehak.dto.cases

import hr.algebra.fakehak.enum.CaseStatus
import java.time.LocalDateTime

data class CaseSummaryResponseDto(
    val id: Long,
    val userName: String,
    val vehicleInfo: String,
    val latitude: Double,
    val longitude: Double,
    val status: CaseStatus,
    val assignedEmployeeName: String?,
    val createdAt: LocalDateTime
)
