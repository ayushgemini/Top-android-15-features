import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConnectivityActivity : AppCompatActivity() {

    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connectivity)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Register connectivity broadcast receiver
        registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private val connectivityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        showToast("Connected to Cellular Network")
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        showToast("Connected to WiFi")
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> {
                        showToast("Connected via Bluetooth")
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        showToast("Connected to Ethernet")
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> {
                        showToast("Connected to Low-Power Wireless")
                    }
                    else -> {
                        // Placeholder for future satellite connectivity
                        showToast("Unknown Network - Satellite support might be added in future")
                    }
                }
            } else {
                showToast("No Network Available")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectivityReceiver)
    }
}
