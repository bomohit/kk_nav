package com.example.kktm_navigation

import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_main)

        val buttonRegister: Button = findViewById(R.id.buttonSignin)
        val username: TextView = findViewById(R.id.si_username)
        val password: TextView = findViewById(R.id.si_password)
        buttonRegister.setOnClickListener {
            val v = it
            if (!valid()) {
                return@setOnClickListener
            } else {
                // Submit register profile to db
                val data = hashMapOf(
                        "username" to username.text.toString(),
                        "password" to password.text.toString()
                )

                db.collection("user").document(username.text.toString())
                        .set(data)
                        .addOnSuccessListener {
                            d("bomoh", "Register Complete")
                            Toast.makeText(applicationContext, "Register Complete", Toast.LENGTH_SHORT)
                            onBackPressed()
                        }
                        .addOnFailureListener { e ->
                            Snackbar.make(v, "Register Failed $e", Snackbar.LENGTH_SHORT)
                        }
            }
        }
    }

    private fun valid(): Boolean {
        var valid = true
        val username = findViewById<TextView>(R.id.si_username)
        val password = findViewById<TextView>(R.id.si_password)
        val confirmPassword = findViewById<TextView>(R.id.re_confirmPassword)

        if (username.text.toString().isEmpty()) {
            username.error = "enter username"
            valid = false
        } else {
            username.error = null
        }

        if (password.text.toString().isEmpty()) {
            password.error = "enter password"
            valid = false
        } else {
            password.error = null
        }

        if (confirmPassword.text.toString().isEmpty()) {
            confirmPassword.error = "re-enter password"
            valid = false
        } else {
            confirmPassword.error = null
            // check if the password == confirm password or not
            if (password.text.toString() != confirmPassword.text.toString()) {
                confirmPassword.error = "enter the same password"
                valid = false
            } else {
                confirmPassword.error = null
            }
        }

        return valid

    }
}