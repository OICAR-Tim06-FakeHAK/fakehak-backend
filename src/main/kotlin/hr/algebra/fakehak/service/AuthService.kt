package hr.algebra.fakehak.service

import hr.algebra.fakehak.dto.login.LoginRequestDto
import hr.algebra.fakehak.dto.login.LoginResponseDto

interface AuthService {
    fun login(request: LoginRequestDto): LoginResponseDto
}
