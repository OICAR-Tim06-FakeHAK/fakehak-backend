package hr.algebra.fakehak.controller

import hr.algebra.fakehak.dto.cases.CaseAssignRequestDto
import hr.algebra.fakehak.dto.cases.CaseCreateRequestDto
import hr.algebra.fakehak.dto.cases.CaseResponseDto
import hr.algebra.fakehak.dto.cases.CaseSummaryResponseDto
import hr.algebra.fakehak.dto.cases.CaseUpdateLocationRequestDto
import hr.algebra.fakehak.dto.cases.CaseUpdateStatusRequestDto
import hr.algebra.fakehak.enum.CaseStatus
import hr.algebra.fakehak.service.CaseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cases")
class CaseController(private val caseService: CaseService) {

    @PostMapping
    fun create(@Valid @RequestBody request: CaseCreateRequestDto): ResponseEntity<CaseResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(caseService.create(request))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun getById(@PathVariable id: Long): ResponseEntity<CaseResponseDto> {
        return ResponseEntity.ok(caseService.getById(id))
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun getAll(): ResponseEntity<List<CaseSummaryResponseDto>> {
        return ResponseEntity.ok(caseService.getAll())
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun getByStatus(@PathVariable status: CaseStatus): ResponseEntity<List<CaseSummaryResponseDto>> {
        return ResponseEntity.ok(caseService.getByStatus(status))
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun getActiveCases(): ResponseEntity<List<CaseSummaryResponseDto>> {
        return ResponseEntity.ok(caseService.getActiveCases())
    }

    @GetMapping("/user/{userId}")
    fun getByUserId(@PathVariable userId: Long): ResponseEntity<List<CaseSummaryResponseDto>> {
        return ResponseEntity.ok(caseService.getByUserId(userId))
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun updateStatus(
        @PathVariable id: Long,
        @Valid @RequestBody request: CaseUpdateStatusRequestDto
    ): ResponseEntity<CaseResponseDto> {
        return ResponseEntity.ok(caseService.updateStatus(id, request))
    }

    @PatchMapping("/{id}/assign")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun assignEmployee(
        @PathVariable id: Long,
        @Valid @RequestBody request: CaseAssignRequestDto
    ): ResponseEntity<CaseResponseDto> {
        return ResponseEntity.ok(caseService.assignEmployee(id, request))
    }

    @PatchMapping("/{id}/location")
    fun updateLocation(
        @PathVariable id: Long,
        @Valid @RequestBody request: CaseUpdateLocationRequestDto
    ): ResponseEntity<CaseResponseDto> {
        return ResponseEntity.ok(caseService.updateLocation(id, request))
    }
}