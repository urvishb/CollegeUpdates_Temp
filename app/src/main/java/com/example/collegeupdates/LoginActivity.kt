package com.example.collegeupdates

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null)
        {
            goMainActivity()
        }

        btnLogin.setOnClickListener {
            btnLogin.isEnabled = false
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if(email.isBlank() || password.isBlank())
            {
                Toast.makeText(this, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }

            // Firebase Authentication Check >>>

            // An asynchronous operation
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {

                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    goMainActivity()

                }
                else {
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goMainActivity() {
        Log.i(TAG, "goMainActivity")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}