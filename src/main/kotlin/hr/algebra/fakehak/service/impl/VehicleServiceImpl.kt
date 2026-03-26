package hr.algebra.fakehak.service.impl

import hr.algebra.fakehak.dto.vehicle.VehicleCreateRequestDto
import hr.algebra.fakehak.dto.vehicle.VehicleResponseDto
import hr.algebra.fakehak.dto.vehicle.VehicleUpdateRequestDto
import hr.algebra.fakehak.exception.DuplicateResourceException
import hr.algebra.fakehak.exception.InvalidOperationException
import hr.algebra.fakehak.exception.ResourceNotFoundException
import hr.algebra.fakehak.model.Vehicle
import hr.algebra.fakehak.repository.UserRepository
import hr.algebra.fakehak.repository.VehicleRepository
import hr.algebra.fakehak.service.VehicleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VehicleServiceImpl(
    private val vehicleRepository: VehicleRepository,
    private val userRepository: UserRepository
) : VehicleService {

    override fun addVehicle(userId: Long, request: VehicleCreateRequestDto): VehicleResponseDto {
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("User with id $userId not found") }

        if (vehicleRepository.existsByVin(request.vin)) {
            throw DuplicateResourceException("Vehicle with VIN '${request.vin}' already exists")
        }

        val vehicle = Vehicle(
            brand = request.brand,
            model = request.model,
            vin = request.vin,
            registrationPlate = request.registrationPlate,
            firstRegistrationDate = request.firstRegistrationDate,
            user = user
        )
        return vehicleRepository.save(vehicle).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getByUserId(userId: Long): List<VehicleResponseDto> {
        return vehicleRepository.findAllByUserId(userId).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getById(userId: Long, vehicleId: Long): VehicleResponseDto {
        return findVehicleByIdAndOwner(vehicleId, userId).toResponse()
    }

    override fun update(userId: Long, vehicleId: Long, request: VehicleUpdateRequestDto): VehicleResponseDto {
        val vehicle = findVehicleByIdAndOwner(vehicleId, userId)

        request.brand?.let { vehicle.brand = it }
        request.model?.let { vehicle.model = it }
        request.registrationPlate?.let { vehicle.registrationPlate = it }
        request.firstRegistrationDate?.let { vehicle.firstRegistrationDate = it }

        return vehicleRepository.save(vehicle).toResponse()
    }

    override fun delete(userId: Long, vehicleId: Long) {
        val vehicle = findVehicleByIdAndOwner(vehicleId, userId)
        vehicleRepository.delete(vehicle)
    }

    private fun findVehicleByIdAndOwner(vehicleId: Long, userId: Long): Vehicle {
        val vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow { ResourceNotFoundException("Vehicle with id $vehicleId not found") }

        if (vehicle.user.id != userId) {
            throw InvalidOperationException("Vehicle $vehicleId does not belong to user $userId")
        }
        return vehicle
    }

    private fun Vehicle.toResponse() = VehicleResponseDto(
        id = id,
        brand = brand,
        model = model,
        vin = vin,
        registrationPlate = registrationPlate,
        firstRegistrationDate = firstRegistrationDate,
        createdAt = createdAt
    )
}