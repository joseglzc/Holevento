package es.ideas.holaevento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPass: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrarse: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_act)

        auth = Firebase.auth

        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        btnIniciarSesion = findViewById(R.id.btnRegistro)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)

        btnIniciarSesion.setOnClickListener {
            val email = loginEmail.text.toString()
            val pass = loginPass.text.toString()

            if(compruebaVacio(email, pass)) {
                loguear(email, pass)
            }
        }

        btnRegistrarse.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

    }

    private fun loguear(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Se ha producido un error al iniciar sesión", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun compruebaVacio(email: String, pass: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty()
    }


}