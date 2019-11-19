package com.incwell.blackforest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.R
import kotlinx.android.synthetic.main.activity_password_reset.*
import org.koin.android.ext.android.inject

class PasswordResetActivity : AppCompatActivity() {

    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        sendMailBtn.setOnClickListener { btn ->
            pb_sendMail.visibility = View.VISIBLE
            authenticationViewModel.resetPassword(passwordReset_email.text.toString())
            authenticationViewModel.messageResponse.observe(this, Observer {
                pb_sendMail.visibility = View.GONE
                Snackbar.make(btn, it, Snackbar.LENGTH_SHORT).show()
            })
        }
    }
}
