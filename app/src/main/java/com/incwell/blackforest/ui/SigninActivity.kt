package com.incwell.blackforest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.MainActivity
import com.incwell.blackforest.R
import com.incwell.blackforest.util.handleEmptyError
import com.incwell.blackforest.util.hideErrorHint
import com.incwell.blackforest.util.showSnackbar
import kotlinx.android.synthetic.main.activity_signin.*
import org.koin.android.ext.android.inject

class SigninActivity : AppCompatActivity() {
    private val authenticationViewModel: AuthenticationViewModel by inject()

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        login_btn.setOnClickListener { view ->
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
                    progressBar.visibility = View.VISIBLE
                    authenticationViewModel.signIn(
                        signin_username.text.toString().split(" ")[0],
                        signin_password.text.toString().split(" ")[0]
                    )

                    authenticationViewModel.status.observe(this, Observer {
                        progressBar.visibility=View.GONE
                        if(it){
                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                            finish()
                        }else{
                            showSnackbar(view,"Invalid Credentials.")
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
//            googleSignIn()
            showSnackbar(it,"Coming soon!")
        }

        fb_sign_in_button.setOnClickListener {
            showSnackbar(it,"Coming soon!")
        }

        register_btn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
        }

        forgot_btn.setOnClickListener {
            val intent = Intent(this, PasswordResetActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
        }
        hideError()
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1001)
    }

    private fun signOut(view: View) {
        googleSignInClient.signOut().addOnCompleteListener {
            showSnackbar(view,"Signout successfully")
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

    private fun hideError() {
        hideErrorHint(signin_username, til_signin_username)
        hideErrorHint(signin_password, til_signin_password)
    }
}
