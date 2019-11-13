package com.incwell.blackforest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.AuthenticationViewModel
import com.incwell.blackforest.ui.SigninActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {

            val response = authenticationViewModel.getCities()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    splashLoading.visibility = View.GONE
                }

                SharedPref.resetCity() //delete all saved cities from Hawk to update
                for (i in response.body()!!.indices) {
                    SharedPref.saveCity(response.body()!![i])
                }
                //checking if token exist in sharedpreference or not
                authenticationViewModel.sharedPreference()
                if (authenticationViewModel.isPresent!!) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, SigninActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Log.d("cities", "${response}")
            }
        }
    }
}
