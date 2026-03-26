package hr.algebra.fakehak.service

import hr.algebra.fakehak.dto.cases.CaseAssignRequestDto
import hr.algebra.fakehak.dto.cases.CaseCreateRequestDto
import hr.algebra.fakehak.dto.cases.CaseResponseDto
import hr.algebra.fakehak.dto.cases.CaseSummaryResponseDto
import hr.algebra.fakehak.dto.cases.CaseUpdateLocationRequestDto
import hr.algebra.fakehak.dto.cases.CaseUpdateStatusRequestDto
import hr.algebra.fakehak.enum.CaseStatus

interface CaseService {
    fun create(request: CaseCreateRequestDto): CaseResponseDto
    fun getById(id: Long): CaseResponseDto
    fun getAll(): List<CaseSummaryResponseDto>
    fun getByStatus(status: CaseStatus): List<CaseSummaryResponseDto>
    fun getActiveCases(): List<CaseSummaryResponseDto>
    fun getByUserId(userId: Long): List<CaseSummaryResponseDto>
    fun updateStatus(id: Long, request: CaseUpdateStatusRequestDto): CaseResponseDto
    fun assignEmployee(id: Long, request: CaseAssignRequestDto): CaseResponseDto
    fun updateLocation(id: Long, request: CaseUpdateLocationRequestDto): CaseResponseDto
}