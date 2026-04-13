package hr.algebra.fakehak.service.impl

import hr.algebra.fakehak.dto.login.LoginRequestDto
import hr.algebra.fakehak.dto.login.LoginResponseDto
import hr.algebra.fakehak.exception.InvalidOperationException
import hr.algebra.fakehak.repository.EmployeeRepository
import hr.algebra.fakehak.repository.UserRepository
import hr.algebra.fakehak.security.JwtService
import hr.algebra.fakehak.service.AuthService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) : AuthService {

    override fun login(request: LoginRequestDto): LoginResponseDto {
        // Try employee login by username
        val employee = employeeRepository.findByUsername(request.identifier)
        if (employee != null) {
            if (!employee.active) {
                throw InvalidOperationException("Account is deactivated")
            }
            if (!passwordEncoder.matches(request.password, employee.password)) {
                throw InvalidOperationException("Invalid credentials")
            }
            val token = jwtService.generateToken(employee.username, employee.role.name)
            return LoginResponseDto(token = token, role = employee.role.name)
        }

        // Try user login by email
        val user = userRepository.findByEmail(request.identifier).orElse(null)
        if (user != null) {
            if (!passwordEncoder.matches(request.password, user.password)) {
                throw InvalidOperationException("Invalid credentials")
            }
            val token = jwtService.generateToken(user.email, "USER")
            return LoginResponseDto(token = token, role = "USER")
        }

        throw InvalidOperationException("Invalid credentials")
    }
}