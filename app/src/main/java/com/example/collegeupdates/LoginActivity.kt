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

        /* We create an instance of the firebase auth as the very first step
        firebaseAuth is the object which carries like a copy of our database in an object
        It is the entry point of the Firebase Authentication SDK.
        We obtain an instance of this class by calling getInstance().

        through auth now we can use the firebase so called predefined methods
        which will help us in the development
        */
        val auth = FirebaseAuth.getInstance()



        /* The concept of user session, as firebase stores the info of the current Logged in user
        in other words the session is saved.
        we could use this data to prevent the app to go to the login activity at the launch
        if the user has already been logged in when she launched the app previously

         */
        if(auth.currentUser != null) // if currentUser data is saved
        {
            goMainActivity() // straight away launch the application's MainActivity, just continue the previous session
        }


        btnLogin.setOnClickListener {
            btnLogin.isEnabled = false // as soon as login btn is clicked, disable it until login task is completed to avoid multiple login actions
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if(email.isBlank() || password.isBlank())
            {
                Toast.makeText(this, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }

            // Firebase Authentication Check >>>

            // Signing in the user
            /* signInWithEmailAndPassword() is a predefined method, as this involves talking to a server over the internet, these things asynchronous
            which means they might take an indeterminate amount of time, so whenever me handle async operations, we make a call and we ask to get notified
            when the task has succeeded. This is where the below mentioned method addOnCompleteListener comes into play, and this method as we can see has a task
            which will hold the status of user Authentication, as in the async operation succeeded or not
            */
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                btnLogin.isEnabled = true // we got the result so we enable it here
                // if the task was successful, we log in the user to the main Activity
                if (task.isSuccessful) {

                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    // Logging in the user, or opening the main activity for her
                    goMainActivity()

                }
                else {
                    // else the task failed, and we show a message (toast) of authentication failure.
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }


        // Sight mode is cleverly adds a dummy/fake/guest user to the app and the user uses the app anonymously through the fake account
        // which is registered on our database as "guest@test.com"
        // see, it mimics the whole sign in procedure but this time the email ID and password value are hardcoded and are
        // filled in when the sightModeBtn pressed
        sightModeBtn.setOnClickListener {
            sightModeBtn.isEnabled = false
            val sightEmail = "guest@test.com"
            val sightPassword = "password"

            if(sightEmail.isBlank() || sightPassword.isBlank())
            {
                Toast.makeText(this, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }

            // Firebase Auth >>>

            auth.signInWithEmailAndPassword(sightEmail, sightPassword).addOnCompleteListener { task ->
                sightModeBtn.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(this, "Welcome to Sight Mode", Toast.LENGTH_SHORT).show()
                    goMainActivity()
                }
                else {
                    Log.e(TAG, "signInWithEmail failed (Sight Mode)", task.exception)
                    Toast.makeText(this, "Authentication failed (Sight Mode)", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goMainActivity() {
        Log.i(TAG, "goMainActivity")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        // this finish(), kills the login activity (once the login is successful) and removes it from the back stack
        // so that on hitting the back button after a successful login we just move out of the app
        // and not just revisit the login Activity
        finish()
    }
}