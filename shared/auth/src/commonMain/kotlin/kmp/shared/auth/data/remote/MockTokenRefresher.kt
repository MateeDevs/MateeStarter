package kmp.shared.auth.data.remote

internal class MockTokenRefresher : TokenRefresher {
    override suspend fun refresh(): String? {
        return "mockToken"
    }
}
