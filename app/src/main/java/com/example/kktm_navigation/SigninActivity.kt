package com.example.kktm_navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_main)

        val buttonSignin: Button = findViewById(R.id.buttonSignin)
        val username: TextView = findViewById(R.id.si_username)
        val password: TextView = findViewById(R.id.si_password)
        val si_register: TextView = findViewById(R.id.si_register)

        buttonSignin.setOnClickListener {
            if (valid()) {
                db.collection("user").document(username.text.toString())
                    .get()
                    .addOnSuccessListener {
                        val uid = it.getField<String>("username").toString()
                        if (uid == username.text.toString()) {
                            val pass = it.getField<String>("password").toString()
                            if (pass == password.text.toString()) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
            }
        }

        si_register.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    private fun valid(): Boolean {
        var valid = true
        val username: TextView = findViewById(R.id.si_username)
        val password: TextView = findViewById(R.id.si_password)

        if (username.text.toString().isEmpty()) {
            username.error = "Required"
            valid = false
        } else {
            username.error = null
        }

        if (password.text.toString().isEmpty()) {
            password.error = "Required"
            valid = false
        } else {
            password.error = null
        }

        return valid
    }
}