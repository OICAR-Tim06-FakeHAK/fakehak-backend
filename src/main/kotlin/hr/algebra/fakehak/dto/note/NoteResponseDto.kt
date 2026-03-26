package hr.algebra.fakehak.dto.note

import java.time.LocalDateTime

data class NoteResponseDto(
    val id: Long,
    val content: String,
    val employeeName: String,
    val createdAt: LocalDateTime
)
