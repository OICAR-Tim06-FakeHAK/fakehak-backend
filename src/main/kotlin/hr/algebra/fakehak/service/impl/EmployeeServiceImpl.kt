package hr.algebra.fakehak.service.impl

import hr.algebra.fakehak.dto.employee.EmployeeCreateRequestDto
import hr.algebra.fakehak.dto.employee.EmployeeResponseDto
import hr.algebra.fakehak.dto.employee.EmployeeUpdateRequestDto
import hr.algebra.fakehak.exception.DuplicateResourceException
import hr.algebra.fakehak.exception.ResourceNotFoundException
import hr.algebra.fakehak.model.Employee
import hr.algebra.fakehak.repository.EmployeeRepository
import hr.algebra.fakehak.service.EmployeeService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val passwordEncoder: PasswordEncoder
) : EmployeeService {

    override fun create(request: EmployeeCreateRequestDto): EmployeeResponseDto {
        if (employeeRepository.existsByUsername(request.username)) {
            throw DuplicateResourceException("Username '${request.username}' is already taken")
        }

        val employee = Employee(
            username = request.username,
            password = passwordEncoder.encode(request.password)!!,
            firstName = request.firstName,
            lastName = request.lastName,
            role = request.role
        )
        return employeeRepository.save(employee).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): EmployeeResponseDto {
        return findEmployeeById(id).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<EmployeeResponseDto> {
        return employeeRepository.findAll().map { it.toResponse() }
    }

    override fun update(id: Long, request: EmployeeUpdateRequestDto): EmployeeResponseDto {
        val employee = findEmployeeById(id)

        request.firstName?.let { employee.firstName = it }
        request.lastName?.let { employee.lastName = it }
        request.role?.let { employee.role = it }
        request.active?.let { employee.active = it }

        return employeeRepository.save(employee).toResponse()
    }

    override fun delete(id: Long) {
        val employee = findEmployeeById(id)
        employee.active = false
        employeeRepository.save(employee)
    }

    private fun findEmployeeById(id: Long): Employee {
        return employeeRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Employee with id $id not found") }
    }

    private fun Employee.toResponse() = EmployeeResponseDto(
        id = id,
        username = username,
        firstName = firstName,
        lastName = lastName,
        role = role,
        active = active,
        createdAt = createdAt
    )
}