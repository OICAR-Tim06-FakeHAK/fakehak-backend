package hr.algebra.fakehak.service.impl

import hr.algebra.fakehak.dto.user.UserRegistrationRequestDto
import hr.algebra.fakehak.dto.user.UserResponseDto
import hr.algebra.fakehak.dto.user.UserUpdateRequestDto
import hr.algebra.fakehak.dto.vehicle.VehicleResponseDto
import hr.algebra.fakehak.exception.DuplicateResourceException
import hr.algebra.fakehak.exception.ResourceNotFoundException
import hr.algebra.fakehak.model.User
import hr.algebra.fakehak.model.Vehicle
import hr.algebra.fakehak.repository.UserRepository
import hr.algebra.fakehak.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun register(request: UserRegistrationRequestDto): UserResponseDto {
        if (userRepository.existsByEmail(request.email)) {
            throw DuplicateResourceException("Email '${request.email}' is already registered")
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber)) {
            throw DuplicateResourceException("Phone number '${request.phoneNumber}' is already registered")
        }

        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = request.phoneNumber,
            email = request.email,
            password = passwordEncoder.encode(request.password)!!
        )
        return userRepository.save(user).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): UserResponseDto {
        return findUserById(id).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<UserResponseDto> {
        return userRepository.findAll().map { it.toResponse() }
    }

    override fun update(id: Long, request: UserUpdateRequestDto): UserResponseDto {
        val user = findUserById(id)

        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }
        request.phoneNumber?.let {
            if (it != user.phoneNumber && userRepository.existsByPhoneNumber(it)) {
                throw DuplicateResourceException("Phone number '$it' is already registered")
            }
            user.phoneNumber = it
        }
        request.email?.let {
            if (it != user.email && userRepository.existsByEmail(it)) {
                throw DuplicateResourceException("Email '$it' is already registered")
            }
            user.email = it
        }

        return userRepository.save(user).toResponse()
    }

    override fun delete(id: Long) {
        val user = findUserById(id)
        userRepository.delete(user)
    }

    private fun findUserById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User with id $id not found") }
    }

    private fun User.toResponse() = UserResponseDto(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        email = email,
        accountStatus = accountStatus,
        vehicles = vehicles.map { it.toVehicleResponse() },
        createdAt = createdAt
    )

    private fun Vehicle.toVehicleResponse() = VehicleResponseDto(
        id = id,
        brand = brand,
        model = model,
        vin = vin,
        registrationPlate = registrationPlate,
        firstRegistrationDate = firstRegistrationDate,
        createdAt = createdAt
    )
}