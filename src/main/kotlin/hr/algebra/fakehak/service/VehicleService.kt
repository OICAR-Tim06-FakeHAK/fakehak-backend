package hr.algebra.fakehak.service

import hr.algebra.fakehak.dto.vehicle.VehicleCreateRequestDto
import hr.algebra.fakehak.dto.vehicle.VehicleResponseDto
import hr.algebra.fakehak.dto.vehicle.VehicleUpdateRequestDto

interface VehicleService {
    fun addVehicle(userId: Long, request: VehicleCreateRequestDto): VehicleResponseDto
    fun getByUserId(userId: Long): List<VehicleResponseDto>
    fun getById(userId: Long, vehicleId: Long): VehicleResponseDto
    fun update(userId: Long, vehicleId: Long, request: VehicleUpdateRequestDto): VehicleResponseDto
    fun delete(userId: Long, vehicleId: Long)
}