package hr.algebra.fakehak.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "vehicles")
class Vehicle(
    @Column(nullable = false)
    var brand: String,

    @Column(nullable = false)
    var model: String,

    @Column(nullable = false, unique = true)
    var vin: String,

    @Column(nullable = false)
    var registrationPlate: String,

    @Column(nullable = false)
    var firstRegistrationDate: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) : BaseEntity()
