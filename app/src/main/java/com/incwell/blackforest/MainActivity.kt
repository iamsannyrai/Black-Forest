package com.incwell.blackforest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.AuthenticationViewModel
import com.incwell.blackforest.ui.SigninActivity
import com.incwell.blackforest.ui.category.subCategory.SubCategoryViewModel
import com.incwell.blackforest.ui.home.HomeViewModel
import com.incwell.blackforest.ui.product.ProductActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val homeViewModel: HomeViewModel by inject()
    val subCategoryViewModel: SubCategoryViewModel by inject()
    val authenticationViewModel: AuthenticationViewModel by inject()

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about, R.id.nav_menu,
                R.id.nav_contact
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menu!!.findItem(R.id.action_cart).setActionView(R.layout.custom_cart_layout)

        if (SharedPref.getCart().size > 0) {
            val cartView =
                menu.findItem(R.id.action_cart).actionView.findViewById<TextView>(R.id.cart_badge)
            cartView.visibility = View.VISIBLE
            cartView.text = SharedPref.getCart().size.toString()
        }

        menu.findItem(R.id.action_cart).actionView.setOnClickListener {
            Toast.makeText(this, "cart clicked", Toast.LENGTH_LONG).show()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                authenticationViewModel.onLogoutButtonClicked()
                val intent = Intent(this, SigninActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }
}
