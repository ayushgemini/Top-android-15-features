import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import java.time.ZonedDateTime
import java.time.ZoneOffset

class HealthConnectActivity : AppCompatActivity() {

    private lateinit var healthConnectClient: HealthConnectClient
    private lateinit var stepsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_connect)

        stepsTextView = findViewById(R.id.stepsTextView)

        // Initialize Health Connect client
        healthConnectClient = HealthConnectClient.getOrCreate(this)

        // Request permissions to access step data
        requestHealthPermissions()
    }

    private fun requestHealthPermissions() {
        val permissions = setOf(
            PermissionController.Permission.READ_STEPS,
            PermissionController.Permission.WRITE_STEPS
        )

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsGranted ->
            if (permissionsGranted.containsValue(true)) {
                // Permissions granted, proceed with reading or writing data
                readStepCountData()
            } else {
                // Handle permission denial
            }
        }
        requestPermissionLauncher.launch(permissions.toTypedArray())
    }

    private fun readStepCountData() {
        // Define time range for data retrieval
        val timeRangeFilter = TimeRangeFilter.between(
            startTime = ZonedDateTime.now().minusDays(1).toInstant(),
            endTime = Instant.now()
        )

        // Fetch step count data
        healthConnectClient.aggregate(
            StepsRecord.STEPS,
            timeRangeFilter
        ).addOnSuccessListener { steps ->
            // Display steps in the TextView
            stepsTextView.text = "Steps in last 24 hours: ${steps.total}"
        }
    }

    private fun writeStepCountData(steps: Long) {
        val now = Instant.now()
        val stepRecord = StepsRecord(
            count = steps,
            startTime = now.minusSeconds(3600), // 1 hour ago
            endTime = now
        )

        healthConnectClient.insertRecords(listOf(stepRecord))
            .addOnSuccessListener {
                // Steps recorded successfully
                stepsTextView.text = "Successfully recorded $steps steps"
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
