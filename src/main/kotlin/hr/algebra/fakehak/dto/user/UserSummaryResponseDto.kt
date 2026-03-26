package hr.algebra.fakehak.dto.user

import hr.algebra.fakehak.enum.AccountStatus

data class UserSummaryResponseDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val accountStatus: AccountStatus
)
