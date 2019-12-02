package com.incwell.blackforest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.R
import com.incwell.blackforest.util.handleEmptyError
import com.incwell.blackforest.util.hideErrorHint
import com.incwell.blackforest.util.showSnackbar
import kotlinx.android.synthetic.main.activity_password_reset.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.android.ext.android.inject

class PasswordResetActivity : AppCompatActivity() {

    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        sendMailBtn.setOnClickListener { btn ->
            if (passwordResetEmail.text.isNullOrEmpty()) {
                handleEmptyError(til_passwordResetEmail)
                return@setOnClickListener
            } else {
                pb_sendMail.visibility = View.VISIBLE
                authenticationViewModel.resetPassword(passwordResetEmail.text.toString())
                authenticationViewModel.messageResponse.observe(this, Observer {
                    pb_sendMail.visibility = View.GONE
                    showSnackbar(btn,it)
                })
            }
        }
        cancelMailBtn.setOnClickListener {
            onBackPressed()
        }

        hideErrorHint(passwordResetEmail, til_passwordResetEmail)
    }
}
