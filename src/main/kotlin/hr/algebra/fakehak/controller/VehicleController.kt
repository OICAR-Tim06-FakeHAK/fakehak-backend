package hr.algebra.fakehak.controller

import hr.algebra.fakehak.dto.vehicle.VehicleCreateRequestDto
import hr.algebra.fakehak.dto.vehicle.VehicleResponseDto
import hr.algebra.fakehak.dto.vehicle.VehicleUpdateRequestDto
import hr.algebra.fakehak.service.VehicleService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}/vehicles")
class VehicleController(private val vehicleService: VehicleService) {

    @PostMapping
    fun addVehicle(
        @PathVariable userId: Long,
        @Valid @RequestBody request: VehicleCreateRequestDto
    ): ResponseEntity<VehicleResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.addVehicle(userId, request))
    }

    @GetMapping
    fun getByUserId(@PathVariable userId: Long): ResponseEntity<List<VehicleResponseDto>> {
        return ResponseEntity.ok(vehicleService.getByUserId(userId))
    }

    @GetMapping("/{vehicleId}")
    fun getById(@PathVariable userId: Long, @PathVariable vehicleId: Long): ResponseEntity<VehicleResponseDto> {
        return ResponseEntity.ok(vehicleService.getById(userId, vehicleId))
    }

    @PutMapping("/{vehicleId}")
    fun update(
        @PathVariable userId: Long,
        @PathVariable vehicleId: Long,
        @Valid @RequestBody request: VehicleUpdateRequestDto
    ): ResponseEntity<VehicleResponseDto> {
        return ResponseEntity.ok(vehicleService.update(userId, vehicleId, request))
    }

    @DeleteMapping("/{vehicleId}")
    fun delete(@PathVariable userId: Long, @PathVariable vehicleId: Long): ResponseEntity<Void> {
        vehicleService.delete(userId, vehicleId)
        return ResponseEntity.noContent().build()
    }
}