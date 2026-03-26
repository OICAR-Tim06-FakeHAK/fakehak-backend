package hr.algebra.fakehak.service.impl

import hr.algebra.fakehak.dto.cases.CaseAssignRequestDto
import hr.algebra.fakehak.dto.cases.CaseCreateRequestDto
import hr.algebra.fakehak.dto.cases.CaseResponseDto
import hr.algebra.fakehak.dto.cases.CaseSummaryResponseDto
import hr.algebra.fakehak.dto.cases.CaseUpdateLocationRequestDto
import hr.algebra.fakehak.dto.cases.CaseUpdateStatusRequestDto
import hr.algebra.fakehak.dto.employee.EmployeeResponseDto
import hr.algebra.fakehak.dto.note.NoteResponseDto
import hr.algebra.fakehak.dto.user.UserSummaryResponseDto
import hr.algebra.fakehak.dto.vehicle.VehicleResponseDto
import hr.algebra.fakehak.enum.CaseStatus
import hr.algebra.fakehak.exception.ResourceNotFoundException
import hr.algebra.fakehak.model.Case
import hr.algebra.fakehak.model.Employee
import hr.algebra.fakehak.model.Note
import hr.algebra.fakehak.model.User
import hr.algebra.fakehak.model.Vehicle
import hr.algebra.fakehak.repository.CaseRepository
import hr.algebra.fakehak.repository.EmployeeRepository
import hr.algebra.fakehak.repository.UserRepository
import hr.algebra.fakehak.repository.VehicleRepository
import hr.algebra.fakehak.service.CaseService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CaseServiceImpl(
    private val caseRepository: CaseRepository,
    private val userRepository: UserRepository,
    private val vehicleRepository: VehicleRepository,
    private val employeeRepository: EmployeeRepository
) : CaseService {

    override fun create(request: CaseCreateRequestDto): CaseResponseDto {
        val user = userRepository.findById(request.userId)
            .orElseThrow { ResourceNotFoundException("User with id ${request.userId} not found") }
        val vehicle = vehicleRepository.findById(request.vehicleId)
            .orElseThrow { ResourceNotFoundException("Vehicle with id ${request.vehicleId} not found") }

        val case = Case(
            user = user,
            vehicle = vehicle,
            latitude = request.latitude,
            longitude = request.longitude,
            description = request.description
        )
        return caseRepository.save(case).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): CaseResponseDto {
        return findCaseById(id).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<CaseSummaryResponseDto> {
        return caseRepository.findAll().map { it.toSummary() }
    }

    @Transactional(readOnly = true)
    override fun getByStatus(status: CaseStatus): List<CaseSummaryResponseDto> {
        return caseRepository.findAllByStatus(status).map { it.toSummary() }
    }

    @Transactional(readOnly = true)
    override fun getActiveCases(): List<CaseSummaryResponseDto> {
        return caseRepository.findAllByStatusIn(
            listOf(CaseStatus.ACTIVE, CaseStatus.IN_PROGRESS)
        ).map { it.toSummary() }
    }

    @Transactional(readOnly = true)
    override fun getByUserId(userId: Long): List<CaseSummaryResponseDto> {
        return caseRepository.findAllByUserId(userId).map { it.toSummary() }
    }

    override fun updateStatus(id: Long, request: CaseUpdateStatusRequestDto): CaseResponseDto {
        val case = findCaseById(id)
        case.status = request.status
        return caseRepository.save(case).toResponse()
    }

    override fun assignEmployee(id: Long, request: CaseAssignRequestDto): CaseResponseDto {
        val case = findCaseById(id)
        val employee = employeeRepository.findById(request.employeeId)
            .orElseThrow { ResourceNotFoundException("Employee with id ${request.employeeId} not found") }
        case.assignedEmployee = employee
        return caseRepository.save(case).toResponse()
    }

    override fun updateLocation(id: Long, request: CaseUpdateLocationRequestDto): CaseResponseDto {
        val case = findCaseById(id)
        case.latitude = request.latitude
        case.longitude = request.longitude
        return caseRepository.save(case).toResponse()
    }

    private fun findCaseById(id: Long): Case {
        return caseRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Case with id $id not found") }
    }

    private fun Case.toResponse() = CaseResponseDto(
        id = id,
        user = user.toSummary(),
        vehicle = vehicle.toVehicleResponse(),
        latitude = latitude,
        longitude = longitude,
        description = description,
        status = status,
        assignedEmployee = assignedEmployee?.toEmployeeResponse(),
        notes = notes.map { it.toNoteResponse() },
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun Case.toSummary() = CaseSummaryResponseDto(
        id = id,
        userName = "${user.firstName} ${user.lastName}",
        vehicleInfo = "${vehicle.brand} ${vehicle.model} (${vehicle.registrationPlate})",
        latitude = latitude,
        longitude = longitude,
        status = status,
        assignedEmployeeName = assignedEmployee?.let { "${it.firstName} ${it.lastName}" },
        createdAt = createdAt
    )

    private fun User.toSummary() = UserSummaryResponseDto(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        email = email,
        accountStatus = accountStatus
    )

    private fun Vehicle.toVehicleResponse() = VehicleResponseDto(
        id = id,
        brand = brand,
        model = model,
        vin = vin,
        registrationPlate = registrationPlate,
        firstRegistrationDate = firstRegistrationDate,
        createdAt = createdAt
    )

    private fun Employee.toEmployeeResponse() = EmployeeResponseDto(
        id = id,
        username = username,
        firstName = firstName,
        lastName = lastName,
        role = role,
        active = active,
        createdAt = createdAt
    )

    private fun Note.toNoteResponse() = NoteResponseDto(
        id = id,
        content = content,
        employeeName = "${employee.firstName} ${employee.lastName}",
        createdAt = createdAt
    )
}