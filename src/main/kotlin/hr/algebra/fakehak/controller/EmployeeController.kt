package hr.algebra.fakehak.controller

import hr.algebra.fakehak.dto.employee.EmployeeCreateRequestDto
import hr.algebra.fakehak.dto.employee.EmployeeResponseDto
import hr.algebra.fakehak.dto.employee.EmployeeUpdateRequestDto
import hr.algebra.fakehak.service.EmployeeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employees")
class EmployeeController(private val employeeService: EmployeeService) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun create(@Valid @RequestBody request: EmployeeCreateRequestDto): ResponseEntity<EmployeeResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(request))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun getById(@PathVariable id: Long): ResponseEntity<EmployeeResponseDto> {
        return ResponseEntity.ok(employeeService.getById(id))
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getAll(): ResponseEntity<List<EmployeeResponseDto>> {
        return ResponseEntity.ok(employeeService.getAll())
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: EmployeeUpdateRequestDto): ResponseEntity<EmployeeResponseDto> {
        return ResponseEntity.ok(employeeService.update(id, request))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        employeeService.delete(id)
        return ResponseEntity.noContent().build()
    }
}