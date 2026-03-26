package hr.algebra.fakehak.service.impl

import hr.algebra.fakehak.dto.note.NoteCreateRequestDto
import hr.algebra.fakehak.dto.note.NoteResponseDto
import hr.algebra.fakehak.exception.ResourceNotFoundException
import hr.algebra.fakehak.model.Note
import hr.algebra.fakehak.repository.CaseRepository
import hr.algebra.fakehak.repository.EmployeeRepository
import hr.algebra.fakehak.repository.NoteRepository
import hr.algebra.fakehak.service.NoteService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val caseRepository: CaseRepository,
    private val employeeRepository: EmployeeRepository
) : NoteService {

    override fun addNote(caseId: Long, request: NoteCreateRequestDto): NoteResponseDto {
        val case = caseRepository.findById(caseId)
            .orElseThrow { ResourceNotFoundException("Case with id $caseId not found") }
        val employee = employeeRepository.findById(request.employeeId)
            .orElseThrow { ResourceNotFoundException("Employee with id ${request.employeeId} not found") }

        val note = Note(
            content = request.content,
            towCase = case,
            employee = employee
        )
        return noteRepository.save(note).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getNotesByCase(caseId: Long): List<NoteResponseDto> {
        return noteRepository.findAllByTowCaseIdOrderByCreatedAtAsc(caseId).map { it.toResponse() }
    }

    private fun Note.toResponse() = NoteResponseDto(
        id = id,
        content = content,
        employeeName = "${employee.firstName} ${employee.lastName}",
        createdAt = createdAt
    )
}