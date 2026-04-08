package hr.algebra.fakehak.config

import hr.algebra.fakehak.enum.EmployeeRole
import hr.algebra.fakehak.model.Employee
import hr.algebra.fakehak.repository.EmployeeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class DataInitializer {

    @Bean
    fun initData(
        employeeRepository: EmployeeRepository,
        passwordEncoder: PasswordEncoder
    ) = CommandLineRunner {
        if (!employeeRepository.existsByUsername("admin")) {
            employeeRepository.save(
                Employee(
                    username = "admin",
                    password = passwordEncoder.encode("admin123")!!,
                    firstName = "Admin",
                    lastName = "User",
                    role = EmployeeRole.ADMIN
                )
            )
            println(">>> Default admin created (username: admin, password: admin123)")
        }

        if (!employeeRepository.existsByUsername("dispatcher")) {
            employeeRepository.save(
                Employee(
                    username = "dispatcher",
                    password = passwordEncoder.encode("dispatcher123")!!,
                    firstName = "Dispatcher",
                    lastName = "User",
                    role = EmployeeRole.DISPATCHER
                )
            )
            println(">>> Default dispatcher created (username: dispatcher, password: dispatcher123)")
        }
    }
}