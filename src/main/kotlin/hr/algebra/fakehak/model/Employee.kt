package hr.algebra.fakehak.model

import hr.algebra.fakehak.enum.EmployeeRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "employees")
class Employee(
    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: EmployeeRole = EmployeeRole.DISPATCHER,

    @Column(nullable = false)
    var active: Boolean = true
) : BaseEntity()
