import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView

class PhotoPickerActivity : AppCompatActivity() {

    private lateinit var pickImagesLauncher: ActivityResultLauncher<Intent>
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_picker)

        imageView = findViewById(R.id.selectedImageView)
        val pickImageButton = findViewById<Button>(R.id.pickImageButton)

        // Initialize the ActivityResultLauncher for picking images
        pickImagesLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the selected image URI from result data
                val selectedImageUri: Uri? = result.data?.data
                selectedImageUri?.let { uri ->
                    imageView.setImageURI(uri) // Display the selected image
                }
            }
        }

        pickImageButton.setOnClickListener {
            openPhotoPicker()
        }
    }

    private fun openPhotoPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14 (API level 34)
            val intent = Intent(Intent.ACTION_PICK_IMAGES) // Enhanced Photo Picker action
            intent.type = "image/*"
            pickImagesLauncher.launch(intent) // Launch the Photo Picker
        } else {
            // Fallback for older versions if necessary
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImagesLauncher.launch(intent)
        }
    }
}
