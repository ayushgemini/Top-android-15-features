import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential

class SignInActivity : AppCompatActivity() {

    private lateinit var credentialManager: CredentialManager
    private lateinit var getCredentialLauncher: ActivityResultLauncher<GetCredentialRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize Credential Manager
        credentialManager = CredentialManager.create(this)

        // Set up the credential retrieval launcher
        setupCredentialRetrievalLauncher()

        // Initiate sign-in flow
        requestCredentials()
    }

    private fun setupCredentialRetrievalLauncher() {
        getCredentialLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val credential = result.data?.getParcelableExtra<Credential>(CredentialManager.EXTRA_CREDENTIAL)
            when (credential) {
                is PasswordCredential -> handlePasswordSignIn(credential.password)
                is PublicKeyCredential -> handlePasskeySignIn(credential.rawId) // Handle passkey (FIDO2) based login
                else -> showToast("Unknown credential type.")
            }
        }
    }

    private fun requestCredentials() {
        val request = GetCredentialRequest.Builder()
            .addAllowedCredentialType(PasswordCredential.TYPE_PASSWORD) // Allow password login
            .addAllowedCredentialType(PublicKeyCredential.TYPE_PUBLIC_KEY) // Allow passkey login
            .build()

        getCredentialLauncher.launch(credentialManager.createGetCredentialIntent(request))
    }

    private fun handlePasswordSignIn(password: String) {
        // TODO: Authenticate user with the password
        showToast("Signed in with password.")
    }

    private fun handlePasskeySignIn(passkeyId: ByteArray) {
        // TODO: Authenticate user with the passkey (FIDO2)
        showToast("Signed in with passkey.")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
