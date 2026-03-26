package hr.algebra.fakehak.repository

import hr.algebra.fakehak.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long> {
    fun findAllByTowCaseIdOrderByCreatedAtAsc(caseId: Long): List<Note>
}
