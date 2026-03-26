package hr.algebra.fakehak.repository

import hr.algebra.fakehak.enum.CaseStatus
import hr.algebra.fakehak.model.Case
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CaseRepository : JpaRepository<Case, Long> {
    fun findAllByStatus(status: CaseStatus): List<Case>
    fun findAllByUserId(userId: Long): List<Case>
    fun findAllByAssignedEmployeeId(employeeId: Long): List<Case>
    fun findAllByStatusIn(statuses: List<CaseStatus>): List<Case>
}
