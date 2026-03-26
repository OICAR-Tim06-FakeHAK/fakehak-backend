package hr.algebra.fakehak.controller

import hr.algebra.fakehak.dto.note.NoteCreateRequestDto
import hr.algebra.fakehak.dto.note.NoteResponseDto
import hr.algebra.fakehak.service.NoteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cases/{caseId}/notes")
class NoteController(private val noteService: NoteService) {

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun addNote(
        @PathVariable caseId: Long,
        @Valid @RequestBody request: NoteCreateRequestDto
    ): ResponseEntity<NoteResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.addNote(caseId, request))
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    fun getNotesByCase(@PathVariable caseId: Long): ResponseEntity<List<NoteResponseDto>> {
        return ResponseEntity.ok(noteService.getNotesByCase(caseId))
    }
}