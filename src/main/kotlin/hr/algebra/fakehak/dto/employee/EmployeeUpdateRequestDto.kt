package hr.algebra.fakehak.dto.employee

import hr.algebra.fakehak.enum.EmployeeRole

data class EmployeeUpdateRequestDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val role: EmployeeRole? = null,
    val active: Boolean? = null
)
