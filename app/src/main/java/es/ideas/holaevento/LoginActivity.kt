package es.ideas.holaevento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPass: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrarse: Button
    private lateinit var tvReestrableceer: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.login_act)

        auth = Firebase.auth

        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        btnIniciarSesion = findViewById(R.id.btnRegistro)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        tvReestrableceer = findViewById(R.id.tvOlvidadoPass)

        btnIniciarSesion.setOnClickListener {
            val email = loginEmail.text.toString()
            val pass = loginPass.text.toString()

            if(compruebaVacio(email, pass)) {
                loguear(email, pass)
            } else {
                Toast.makeText(applicationContext, "Rellena los campos", Toast.LENGTH_LONG).show()
            }
        }

        btnRegistrarse.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        tvReestrableceer.setOnClickListener{
            startActivity(Intent(this, RecoveryPassActivity::class.java))
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
                    Toast.makeText(applicationContext, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun compruebaVacio(email: String, pass: String): Boolean {
        if (email.isEmpty() && pass.isEmpty()) {
            Toast.makeText(applicationContext, "Rellena los campos", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()) {
            loginEmail.setError("Campo vacío")
            return false
        }
        if (pass.isEmpty()) {
            loginPass.setError("Campo vacío")
            return false
        }
        return true
    }



}