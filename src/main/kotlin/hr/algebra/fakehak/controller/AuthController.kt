package hr.algebra.fakehak.controller

import hr.algebra.fakehak.dto.login.LoginRequestDto
import hr.algebra.fakehak.dto.login.LoginResponseDto
import hr.algebra.fakehak.exception.InvalidOperationException
import hr.algebra.fakehak.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        return try {
            ResponseEntity.ok(authService.login(request))
        } catch (e: InvalidOperationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}