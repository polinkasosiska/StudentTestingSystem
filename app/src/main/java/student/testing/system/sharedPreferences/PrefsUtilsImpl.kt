package student.testing.system.sharedPreferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_EMAIL = "PREF_EMAIL"
private const val PREF_PASSWORD = "PREF_PASSWORD"

@Singleton
class PrefsUtilsImpl @Inject constructor(private val prefs: SharedPreferences) : PrefsUtils {

    override fun setEmail(string: String) = prefs.edit().putString(PREF_EMAIL, string).apply()
    override fun getEmail(): String = prefs.getString(PREF_EMAIL, "") ?: ""

    override fun setPassword(string: String) = prefs.edit().putString(PREF_PASSWORD, string).apply()
    override fun getPassword(): String = prefs.getString(PREF_PASSWORD, "") ?: ""

    override fun clearData() = prefs.edit().clear().apply()

}