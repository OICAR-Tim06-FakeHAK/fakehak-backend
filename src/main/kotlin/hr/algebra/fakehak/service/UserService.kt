package hr.algebra.fakehak.service

import hr.algebra.fakehak.dto.user.UserRegistrationRequestDto
import hr.algebra.fakehak.dto.user.UserResponseDto
import hr.algebra.fakehak.dto.user.UserUpdateRequestDto

interface UserService {
    fun register(request: UserRegistrationRequestDto): UserResponseDto
    fun getById(id: Long): UserResponseDto
    fun getAll(): List<UserResponseDto>
    fun update(id: Long, request: UserUpdateRequestDto): UserResponseDto
    fun delete(id: Long)
}