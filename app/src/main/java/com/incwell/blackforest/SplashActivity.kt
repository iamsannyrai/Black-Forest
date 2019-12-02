package com.incwell.blackforest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.AuthenticationViewModel
import com.incwell.blackforest.ui.SigninActivity
import com.incwell.blackforest.util.NoInternetException
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.net.SocketTimeoutException

class SplashActivity : AppCompatActivity() {
    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authenticationViewModel.getCities()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        splashLoading.visibility = View.GONE
                    }
                    SharedPref.resetCity() //delete all saved cities from Hawk to update
                    for (i in response.body()!!.indices) {
                        SharedPref.saveCity(response.body()!![i])
                    }
                    //checking if token exist in shared preference or not
                    authenticationViewModel.sharedPreference()
                    if (authenticationViewModel.isPresent!!) {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                        finish()
                    } else {
                        val intent = Intent(this@SplashActivity, SigninActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                        finish()
                    }
                } else {
                    handleError("Oops! We messed up. Please come back later.")
                }
            } catch (e: NoInternetException) {
                handleError("Make sure you have Internet connection.")
            } catch (e: SocketTimeoutException) {
                handleError("Connection Timeout!")
            }
        }
    }

    private suspend fun handleError(message: String) {
        withContext(Dispatchers.Main) {
            splashLoading.visibility = View.GONE
            noInternetMsg.text = message
            noInternetMsg.visibility = View.VISIBLE
        }
    }
}
