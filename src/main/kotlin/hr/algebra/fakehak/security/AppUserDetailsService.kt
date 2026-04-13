package hr.algebra.fakehak.security

import hr.algebra.fakehak.repository.EmployeeRepository
import hr.algebra.fakehak.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(
    private val employeeRepository: EmployeeRepository,
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(identifier: String): UserDetails {
        // Try employee by username
        val employee = employeeRepository.findByUsername(identifier)
        if (employee != null) {
            if (!employee.active) {
                throw UsernameNotFoundException("Employee '$identifier' is deactivated")
            }
            return User.builder()
                .username(employee.username)
                .password(employee.password)
                .authorities(SimpleGrantedAuthority("ROLE_${employee.role.name}"))
                .build()
        }

        // Try user by email
        val appUser = userRepository.findByEmail(identifier).orElse(null)
        if (appUser != null) {
            return User.builder()
                .username(appUser.email)
                .password(appUser.password)
                .authorities(SimpleGrantedAuthority("ROLE_USER"))
                .build()
        }

        throw UsernameNotFoundException("Account '$identifier' not found")
    }
}
