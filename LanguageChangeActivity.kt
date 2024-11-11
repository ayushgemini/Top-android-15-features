import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class LanguageChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Language selection button
        val selectLanguageButton: Button = findViewById(R.id.btn_select_language)
        selectLanguageButton.setOnClickListener {
            openLanguageSelection()
        }
    }

    // Launch language selection dialog
    private fun openLanguageSelection() {
        val languages = arrayOf("English", "French", "Spanish")
        val languageCodes = arrayOf("en", "fr", "es")

        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Select Language")
            .setItems(languages) { dialog, which ->
                val selectedLanguage = languageCodes[which]
                setAppLanguage(selectedLanguage)
            }
            .create()
        dialog.show()
    }

    // Change the app's language based on user selection
    private fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        // Apply the language change to the app in real-time
        resources.updateConfiguration(config, resources.displayMetrics)

        // Restart the activity to reflect the language change
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Close the current activity
    }
}
