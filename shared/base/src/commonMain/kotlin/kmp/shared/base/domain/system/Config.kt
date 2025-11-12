package kmp.shared.base.domain.system

interface Config {
    val isRelease: Boolean
    val apiVariant: ApiVariant
}
