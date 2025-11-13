package kmp.shared.base.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SecureSharedPreferences(
    context: Context,
    fileName: String = "secure_prefs",
) : SharedPreferences {

    private val delegate = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    private val keyAlias = "secure_shared_prefs_key"
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    private fun getOrCreateSecretKey(): SecretKey {
        val existingKey = keyStore.getKey(keyAlias, null) as? SecretKey
        if (existingKey != null) return existingKey

        val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val paramSpec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setUserAuthenticationRequired(false)
            .setRandomizedEncryptionRequired(true)
            .build()

        keyGen.init(paramSpec)
        return keyGen.generateKey()
    }

    private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        val combined = iv + encrypted
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    private fun decrypt(value: String): String {
        val decoded = Base64.decode(value, Base64.NO_WRAP)
        val iv = decoded.copyOfRange(0, 12)
        val encrypted = decoded.copyOfRange(12, decoded.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), GCMParameterSpec(128, iv))
        return String(cipher.doFinal(encrypted), Charsets.UTF_8)
    }

    override fun getAll(): MutableMap<String, *> =
        delegate.all.mapValues { (_, v) ->
            if (v is String) runCatching { decrypt(v) }.getOrNull() ?: v else v
        }.toMutableMap()

    override fun getString(key: String?, defValue: String?): String? =
        delegate.getString(key, null)?.let { runCatching { decrypt(it) }.getOrNull() } ?: defValue

    override fun edit(): SharedPreferences.Editor = SecureEditor(delegate.edit())

    private inner class SecureEditor(private val editor: SharedPreferences.Editor) :
        SharedPreferences.Editor {

        override fun putString(key: String?, value: String?): SharedPreferences.Editor {
            if (key != null && value != null) {
                editor.putString(key, encrypt(value))
            }
            return this
        }

        override fun clear(): SharedPreferences.Editor = apply { editor.clear() }
        override fun apply() = editor.apply()
        override fun commit() = editor.commit()

        override fun putStringSet(key: String?, values: MutableSet<String>?) =
            apply { editor.putStringSet(key, values) }

        override fun putInt(key: String?, value: Int) = apply { editor.putInt(key, value) }
        override fun putLong(key: String?, value: Long) = apply { editor.putLong(key, value) }
        override fun putFloat(key: String?, value: Float) = apply { editor.putFloat(key, value) }
        override fun putBoolean(key: String?, value: Boolean) =
            apply { editor.putBoolean(key, value) }

        override fun remove(key: String?) = apply { editor.remove(key) }
    }

    override fun contains(key: String?): Boolean = delegate.contains(key)
    override fun getBoolean(key: String?, defValue: Boolean) = delegate.getBoolean(key, defValue)
    override fun getFloat(key: String?, defValue: Float) = delegate.getFloat(key, defValue)
    override fun getInt(key: String?, defValue: Int) = delegate.getInt(key, defValue)
    override fun getLong(key: String?, defValue: Long) = delegate.getLong(key, defValue)
    override fun getStringSet(key: String?, defValues: MutableSet<String>?) =
        delegate.getStringSet(key, defValues)

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) =
        delegate.registerOnSharedPreferenceChangeListener(listener)

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) =
        delegate.unregisterOnSharedPreferenceChangeListener(listener)
}
