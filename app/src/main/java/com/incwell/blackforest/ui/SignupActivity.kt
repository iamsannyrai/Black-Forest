package com.incwell.blackforest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import com.incwell.blackforest.Cities
import com.incwell.blackforest.R
import com.incwell.blackforest.util.dropDown
import com.incwell.blackforest.util.hideErrorHint
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.android.ext.android.inject

class SignupActivity : AppCompatActivity() {

    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dropDown(this, Cities, city)

        checkBox.setOnCheckedChangeListener { p0, isChecked -> register.isEnabled = isChecked }
        bindData()
        hideError()

        authenticationViewModel.response.observe(this, Observer {
            if (it) {
                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun bindData() {
        register.setOnClickListener {
            when {
                first_name.text.toString().isEmpty() -> {
                    handleEmptyError(til_first_name)
                    return@setOnClickListener
                }
                last_name.text.toString().isEmpty() -> {
                    handleEmptyError(til_last_name)
                    return@setOnClickListener
                }
                user_name.text.toString().isEmpty() -> {
                    handleEmptyError(til_user_name)
                    return@setOnClickListener
                }
                phone_number.text.toString().isEmpty() -> {
                    handleEmptyError(til_phone_number)
                    return@setOnClickListener
                }
                email.text.toString().isEmpty() -> {
                    handleEmptyError(til_email)
                    return@setOnClickListener
                }
                password.text.toString().isEmpty() -> {
                    handleEmptyError(til_password)
                    return@setOnClickListener
                }
                city.text.toString().isEmpty() -> {
                    handleEmptyError(til_city)
                    return@setOnClickListener
                }
                address.text.toString().isEmpty() -> {
                    handleEmptyError(til_address)
                    return@setOnClickListener
                }

                else -> {
                    authenticationViewModel.registerUser(
                        user_name.text.toString(),
                        first_name.text.toString(),
                        last_name.text.toString(),
                        email.text.toString(),
                        password.text.toString(),
                        1,
                        address.text.toString(),
                        phone_number.text.toString()
                    )
                }

            }
        }
    }

    private fun handleEmptyError(id: TextInputLayout) {
        id.error = "This field is empty!"
        id.requestFocus()
    }

    private fun hideError() {
        hideErrorHint(user_name, til_user_name)
        hideErrorHint(first_name, til_first_name)
        hideErrorHint(last_name, til_last_name)
        hideErrorHint(phone_number, til_phone_number)
        hideErrorHint(email, til_email)
        hideErrorHint(password, til_password)
        hideErrorHint(address, til_address)
    }
}
