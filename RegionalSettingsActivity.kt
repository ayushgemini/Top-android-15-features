import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.*

class RegionalSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regional_settings)

        // Access LocaleManager if the device is running Android 14 or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14 API level
            val localeManager = getSystemService(Context.LOCALE_SERVICE) as LocaleManager

            // Get user-specified locale for this app
            val appLocale: Locale? = localeManager.applicationLocales[0]

            appLocale?.let { locale ->
                // Example: Use the locale to format a number according to the user’s region
                val number = 1234567.89
                val formattedNumber = NumberFormat.getNumberInstance(locale).format(number)

                // Display formatted number in TextView
                val numberTextView = findViewById<TextView>(R.id.numberTextView)
                numberTextView.text = "Formatted Number: $formattedNumber"

                // Display the first day of the week based on user's regional settings
                val firstDayOfWeek = localeManager.firstDayOfWeek
                val firstDayTextView = findViewById<TextView>(R.id.firstDayTextView)
                firstDayTextView.text = "First Day of the Week: $firstDayOfWeek"
            }
        } else {
            // Fallback behavior for devices below Android 14
            // Use default or global locale settings
        }
    }
}
