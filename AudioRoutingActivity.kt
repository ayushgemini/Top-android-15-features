import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class AudioRoutingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Button to start audio playback with smart routing
        val playButton = findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener {
            routeAudioToHearingAid(audioManager)
        }
    }

    private fun routeAudioToHearingAid(audioManager: AudioManager) {
        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        val hasHearingAid = devices.any { device ->
            device.type == AudioDeviceInfo.TYPE_HEARING_AID
        }

        if (hasHearingAid) {
            // Route audio to hearing aids if available
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION)
            // Code to start audio playback here
        } else {
            // Fallback for regular audio output
            audioManager.setMode(AudioManager.MODE_NORMAL)
            // Code to start audio playback here
        }
    }
}
