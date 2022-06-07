package com.example.digimanager.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.digimanager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

class SignUpScreen : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()

        btn_sign_up.setOnClickListener{
            registerUser()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to register a user to our app using the Firebase.
     * For more details visit: https://firebase.google.com/docs/auth/android/custom-auth
     */
    private fun registerUser(){
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        Toast.makeText(
                            this,
                            "$name you have succesfully " +
                                    "registered the email address $registeredEmail",
                            Toast.LENGTH_LONG
                        ).show()

                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            task.exception!!.message, Toast.LENGTH_SHORT
                        )
                            .show()

                    }
                }
        }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }
    }
}

