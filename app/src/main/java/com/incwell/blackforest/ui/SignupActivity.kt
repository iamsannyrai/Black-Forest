package com.incwell.blackforest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.incwell.blackforest.R
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.util.dropDown
import com.incwell.blackforest.util.handleEmptyError
import com.incwell.blackforest.util.hideErrorHint
import com.incwell.blackforest.util.showSnackbar
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.android.ext.android.inject

class SignupActivity : AppCompatActivity() {
    private lateinit var myCity: HashMap<String, Int>

    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        myCity = HashMap()
        for (i in 0 until SharedPref.getCity().size) {
            myCity[SharedPref.getCity()[i].city] = SharedPref.getCity()[i].id
        }

        dropDown(this, myCity.keys.toTypedArray(), city)

        checkBox.setOnCheckedChangeListener { _, isChecked -> register.isEnabled = isChecked }
        bindData()
        hideError()
    }

    private fun bindData() {
        register.setOnClickListener { view ->
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
                        user_name.text.toString().split(" ")[0],
                        first_name.text.toString().split(" ")[0],
                        last_name.text.toString().split(" ")[0],
                        email.text.toString().split(" ")[0],
                        password.text.toString().split(" ")[0],
                        myCity[city.text.toString()]!!,
                        address.text.toString(),
                        phone_number.text.toString().split(" ")[0]
                    )
                    authenticationViewModel.status.observe(this, Observer {
                        if (it) {
                            val intent = Intent(this, SigninActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            showSnackbar(view, "Something went wrong!")
                        }
                    })
                }

            }
        }
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
