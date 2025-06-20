package nekit.corporation.data.local_source

import android.content.Context
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(context: Context) {
    private val sharedPreference = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun save(token: String) {
        sharedPreference.edit().putString(PREF_NAME, token).apply()
    }

    fun getToken(): String? =
        sharedPreference.getString(PREF_NAME, null)


    companion object {
        private const val FILE_NAME = "Token"

        private const val PREF_NAME = "Token"
    }
}