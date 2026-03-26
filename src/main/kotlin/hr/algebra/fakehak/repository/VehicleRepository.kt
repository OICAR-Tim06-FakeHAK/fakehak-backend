package hr.algebra.fakehak.repository

import hr.algebra.fakehak.model.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : JpaRepository<Vehicle, Long> {
    fun findAllByUserId(userId: Long): List<Vehicle>
    fun findByVin(vin: String): Vehicle?
    fun existsByVin(vin: String): Boolean
}
