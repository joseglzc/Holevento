package es.ideas.holaevento

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerEmail: EditText
    private lateinit var registerUsuario: EditText
    private lateinit var registerTelefono: EditText
    private lateinit var registerProvincia: Spinner
    private lateinit var registerPass: EditText
    private lateinit var registerPassR: EditText
    private lateinit var btnRegistro: Button

    private lateinit var auth: FirebaseAuth
    var db = Firebase.firestore

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.register_business_user_act)


        var provincias:ArrayList<Provincia> = ArrayList()

        /*
        db.collection("Provincias")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

         */

        //Hay que mirar los dos a ver con cual lo conseguimos !!!

        val docRef = db.collection("Provincias").document("nombres")
        docRef.get().addOnSuccessListener { task ->
            val provincia = task.toObject<Provincia>()
            Log.d("PROVINCIA", "$task")
            Log.d("PROVINCIA", "$provincia")
        }



        auth = Firebase.auth

        registerEmail = findViewById(R.id.registerEmail)
        registerUsuario = findViewById(R.id.registerUsuario)
        registerTelefono = findViewById(R.id.registerTelefono)
        registerPass = findViewById(R.id.registerPass)
        registerPassR = findViewById(R.id.registerPassR)
        btnRegistro = findViewById(R.id.btnRegistro)

        registerProvincia = findViewById(R.id.provincia)


        ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item).also {
            adapter-> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            registerProvincia.adapter = adapter
        }

        btnRegistro.setOnClickListener {
            val email = registerEmail.text.toString()
            val usuario = registerUsuario.text.toString()
            val telefono = registerTelefono.text.toString()
            val id = registerProvincia.toString()
            val pass = registerPass.text.toString()
            val passR = registerPassR.text.toString()

            if (compruebaVacio(email, usuario, telefono, id, pass, passR)) {
                try {
                    registrar(email, pass)
                    Toast.makeText(this, "Usuario registrado.", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }catch (e: java.lang.Exception){e.toString()}

            }
        }


    }


    private fun registrar(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult?> { task ->
                    if (!task.isSuccessful) {
                        try {
                            throw task.exception!!
                        } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                            Log.d("FALLO", "onComplete: weak_password")
                            Toast.makeText(
                                applicationContext,
                                "Introduce una contraseña de al menos 6 carácteres",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                            Log.d("FALLO", "onComplete: malformed_email")
                            Toast.makeText(
                                applicationContext,
                                "Correo electrónico invalido",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (existEmail: FirebaseAuthUserCollisionException) {
                            Log.d("FALLO", "onComplete: exist_email")
                            Toast.makeText(
                                applicationContext,
                                "Ya existe una cuenta con este correo electrónico",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Log.d("FALLO", "onComplete: " + e.message)
                            Toast.makeText(
                                applicationContext,
                                "Ha ocurrido un error inesperado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )

    }

    //Si todos los campos están rellenos, devuelve true, si no false
    private fun compruebaVacio(
        email: String,
        usuario: String,
        telefono: String,
        id: String,
        pass: String,
        passR: String
    ): Boolean {
        if (email.isEmpty() && usuario.isEmpty() && telefono.isEmpty() && id.isEmpty() && pass.isEmpty() && passR.isEmpty()) {
            Toast.makeText(applicationContext, "Rellena los campos", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()) {
            registerEmail.setError("Campo vacío")
            return false
        }
        if (usuario.isEmpty()) {
            registerUsuario.setError("Campo vacío")
            return false
        }
        if (telefono.isEmpty()) {
            registerTelefono.setError("Campo vacío")
            return false
        }
        if (pass.isEmpty()) {
            registerPass.setError("Campo vacío")
            return false
        }
        if (passR.isEmpty()) {
            registerPassR.setError("Campo vacío")
            return false
        }
        if (pass.isNotEmpty() && passR.isNotEmpty()) {
            if (!pass.equals(passR)) {
                registerPassR.setError("No coincide la contraseña")
                return false
            }
            return true
        }
        return false
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }



}

