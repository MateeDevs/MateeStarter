package kmp.shared.auth.data.remote

internal interface TokenRefresher {
    suspend fun refresh(): String?
}
