package com.incwell.blackforest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.module.authenticationModule
import com.incwell.blackforest.ui.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_password_reset_done.*
import org.koin.android.ext.android.inject

class PasswordResetDoneActivity : AppCompatActivity() {

    private val authenticationViewModel: AuthenticationViewModel by inject()
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset_done)

        val data: Uri? = intent?.data
        val link = data.toString()
        Log.d("password-reset", "$data")

        val path: String = splitLink(link, "$WEB_SERVICE_URL_HTTP/reset/")
        authenticationViewModel.getLink(path)
        authenticationViewModel.userId.observe(this, Observer {
            id = it.toString()
        })

        resetPasswordBtn.setOnClickListener { btn ->
            pb_reset_password_done.visibility = View.VISIBLE
            authenticationViewModel.changePassword(
                new_password_reset.text.toString(),
                confirm_password_reset.text.toString(),
                id.toInt()
            )
            authenticationViewModel.messageResponse.observe(this, Observer {
                pb_reset_password_done.visibility = View.GONE
                Snackbar.make(btn, it, Snackbar.LENGTH_SHORT).show()
            })
        }
    }

    //extract path from url
    private fun splitLink(s1: String, s2: String): String {
        val str2 = Regex(s2)
        val result = str2.containsMatchIn(s1)
        return if (result) {
            s1.replace(str2, "")
        } else {
            ""
        }
    }
}
