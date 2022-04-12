package es.ideas.holaevento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerEmail: EditText
    private lateinit var registerUsuario: EditText
    private lateinit var registerTelefono: EditText
    private lateinit var tBtnID: ToggleButton
    private lateinit var registerID: EditText
    private lateinit var registerPass: EditText
    private lateinit var registerPassR: EditText
    private lateinit var btnRegistro: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_act)

        auth = Firebase.auth

        registerEmail = findViewById(R.id.registerEmail)
        registerUsuario = findViewById(R.id.registerUsuario)
        registerTelefono = findViewById(R.id.registerTelefono)
        tBtnID = findViewById(R.id.tBtnID)
        registerID = findViewById(R.id.registerID)
        registerPass = findViewById(R.id.registerPass)
        registerPassR = findViewById(R.id.registerPassR)
        btnRegistro = findViewById(R.id.btnRegistro)

        btnRegistro.setOnClickListener {
            val email = registerEmail.text.toString()
            val usuario = registerUsuario.text.toString()
            val telefono = registerEmail.text.toString()
            val id = registerID.text.toString()
            val pass = registerPass.text.toString()
            val passR = registerPassR.text.toString()

            if(pass.equals(passR) && compruebaVacio(usuario, telefono, id) && compruebaEmail(email)) {
                registrar(email, pass)
            }
        }

    }

    private fun registrar(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Se ha producido un error en el registro", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun compruebaEmail(email: String): Boolean {
        if("@" in email && "." in email) {
            return true
        }
        return false
    }

    //Si todos los campos están rellenos, devuelve true, si no false
    private fun compruebaVacio(usuario: String, telefono: String, id: String): Boolean {
        return usuario.isNotEmpty() && telefono.isNotEmpty() && id.isNotEmpty()
    }
}