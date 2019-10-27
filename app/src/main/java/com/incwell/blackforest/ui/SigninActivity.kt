package com.incwell.blackforest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.MainActivity
import com.incwell.blackforest.R
import com.incwell.blackforest.util.hideErrorHint
import kotlinx.android.synthetic.main.activity_signin.*
import org.koin.android.ext.android.inject

class SigninActivity : AppCompatActivity() {
    private val authenticationViewModel: AuthenticationViewModel by inject()

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //checking if token exist in sharedpreference or not
        authenticationViewModel.sharedPreference()
        if (authenticationViewModel.isPresent!!) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_btn.setOnClickListener {
            when {
                signin_username.text.toString().isEmpty() -> {
                    handleEmptyError(til_signin_username)
                    return@setOnClickListener
                }
                signin_password.text.toString().isEmpty() -> {
                    handleEmptyError(til_signin_password)
                    return@setOnClickListener
                }
                else -> {
                    authenticationViewModel.signIn(
                        signin_username.text.toString(),
                        signin_password.text.toString()
                    )
                    authenticationViewModel.response.observe(this, Observer {
                        if (it) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })

                }
            }
        }


        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.accessToken))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        google_sign_in_button.setOnClickListener {
            googleSignIn()
        }

        register_btn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }

        hideError()
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1001)
    }

    private fun signOut(view: View) {
        googleSignInClient.signOut().addOnCompleteListener {
            Snackbar.make(view, "Signout successfully", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            //user not signedIn
            Log.i(LOG_TAG, "$account")
        } else {
            //user already signedIn
            Log.i(LOG_TAG, "${account.serverAuthCode}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val googleSignInAccount = task.result
            Log.i(LOG_TAG, "${googleSignInAccount?.idToken}")
        } catch (e: ApiException) {
            Log.i(LOG_TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun handleEmptyError(id: TextInputLayout) {
        id.error = "This field is empty!"
        id.requestFocus()
    }

    private fun hideError() {
        hideErrorHint(signin_username, til_signin_username)
        hideErrorHint(signin_password, til_signin_password)
    }
}
