import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define constraints for the background task
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Requires network connection
            .setRequiresCharging(true) // Requires charging state
            .build()

        // Create a periodic work request with a 6-hour interval
        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncDataWorker>(6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        // Schedule the background task using WorkManager
        WorkManager.getInstance(this).enqueue(syncWorkRequest)
    }
}
