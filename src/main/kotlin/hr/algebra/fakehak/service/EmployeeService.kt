package hr.algebra.fakehak.service

import hr.algebra.fakehak.dto.employee.EmployeeCreateRequestDto
import hr.algebra.fakehak.dto.employee.EmployeeResponseDto
import hr.algebra.fakehak.dto.employee.EmployeeUpdateRequestDto

interface EmployeeService {
    fun create(request: EmployeeCreateRequestDto): EmployeeResponseDto
    fun getById(id: Long): EmployeeResponseDto
    fun getAll(): List<EmployeeResponseDto>
    fun update(id: Long, request: EmployeeUpdateRequestDto): EmployeeResponseDto
    fun delete(id: Long)
}