package hr.algebra.fakehak.controller

import hr.algebra.fakehak.dto.user.UserRegistrationRequestDto
import hr.algebra.fakehak.dto.user.UserResponseDto
import hr.algebra.fakehak.dto.user.UserUpdateRequestDto
import hr.algebra.fakehak.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: UserRegistrationRequestDto): ResponseEntity<UserResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.getById(id))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<UserResponseDto>> {
        return ResponseEntity.ok(userService.getAll())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: UserUpdateRequestDto): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }
}