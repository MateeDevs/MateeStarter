package kmp.shared.base.data.provider

interface AuthProvider {
    var token: String?
    suspend fun refreshToken(): String?
}
