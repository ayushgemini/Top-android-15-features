import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.TypedValue
import android.widget.TextView

class FontSizeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up UI elements
        val textView = findViewById<TextView>(R.id.sampleTextView)
        
        // Adjust font size based on the system's font scale
        adjustFontSize(textView)
    }

    private fun adjustFontSize(textView: TextView) {
        val currentFontScale = resources.configuration.fontScale
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 * currentFontScale) // Adjust base size
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Re-adjust font size when configuration changes (e.g., font size change)
        adjustFontSize(findViewById(R.id.sampleTextView))
    }
}
