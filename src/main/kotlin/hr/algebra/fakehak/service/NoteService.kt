package hr.algebra.fakehak.service

import hr.algebra.fakehak.dto.note.NoteCreateRequestDto
import hr.algebra.fakehak.dto.note.NoteResponseDto

interface NoteService {
    fun addNote(caseId: Long, request: NoteCreateRequestDto): NoteResponseDto
    fun getNotesByCase(caseId: Long): List<NoteResponseDto>
}