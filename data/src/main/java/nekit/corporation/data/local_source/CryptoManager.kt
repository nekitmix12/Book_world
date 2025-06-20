package nekit.corporation.data.local_source

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nekit.corporation.data.remote_source.dto.auth.RefreshRequestDto
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import javax.crypto.Cipher
import javax.inject.Inject

class CryptoManager @Inject constructor(private val context: Context) {

    init {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
            val kpg = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE
            )
            kpg.initialize(
                KeyGenParameterSpec.Builder(
                    KEYSTORE_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                    .build()
            )
            kpg.generateKeyPair()
        }
    }

    fun encryptObject(creds: RefreshRequestDto) {
        val json = Json.encodeToString(creds)
        encryptField(PREFS_KEY, json)
    }

    fun decryptObject(): RefreshRequestDto? {
        val json = decryptField(PREFS_KEY) ?: return null
        return Json.decodeFromString<RefreshRequestDto>(json)
    }

    fun encryptField(prefsKey: String, plainText: String) {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val publicKey = keyStore.getCertificate(KEYSTORE_ALIAS).publicKey

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        val encryptedB64 = Base64.encodeToString(encrypted, Base64.NO_WRAP)

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(prefsKey, encryptedB64).apply()
    }

    fun decryptField(prefsKey: String): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val encryptedB64 = prefs.getString(prefsKey, null) ?: return null
        val encrypted = Base64.decode(encryptedB64, Base64.NO_WRAP)

        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val privateKey = keyStore.getKey(KEYSTORE_ALIAS, null) as PrivateKey

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted, Charsets.UTF_8)
    }

    companion object {
        private const val KEYSTORE_ALIAS = "credKey"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
        private const val PREFS_NAME = "secure_prefs"
        private const val PREFS_KEY = "encrypted_user_data"
    }
}