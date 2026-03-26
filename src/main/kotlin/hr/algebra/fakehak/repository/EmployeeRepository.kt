package hr.algebra.fakehak.repository

import hr.algebra.fakehak.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByUsername(username: String): Employee?
    fun existsByUsername(username: String): Boolean
}
