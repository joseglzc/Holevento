package es.ideas.holaevento

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecoveryPassActivity : AppCompatActivity() {

    private lateinit var btnRecovery: Button
    private lateinit var email: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.rec_pass)

        btnRecovery = findViewById(R.id.btnReestablecer)
        email = findViewById(R.id.recoverEmail)
        auth = FirebaseAuth.getInstance()

        btnRecovery.setOnClickListener{
            auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener { listener ->
                if (listener.isSuccessful) {
                    Toast.makeText(this, "Compruba tu correo electrónico.", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this, "El correo no está registrado.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}