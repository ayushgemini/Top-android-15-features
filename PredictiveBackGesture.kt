import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat


class PredictiveBackGesture : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14 API level
            onBackPressedDispatcher.addCallback(this) { handlePredictiveBackGesture() }
        }
    }


    private fun handlePredictiveBackGesture() {
        // Show a preview or additional action, then navigate or finish
        // Implement any preview UI or behavior here
        // For example: Display a message or animate to the previous screen preview
        
        // If everything is handled, call finish() to actually go back
        finish()
    }
}
