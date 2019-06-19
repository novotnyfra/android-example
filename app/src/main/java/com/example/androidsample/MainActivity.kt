package com.example.androidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.androidsample.databinding.ActivityMainBinding
import com.example.androidsample.util.DrawerController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), DrawerController {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // replace splash screen
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        // Set up Action Bar and Navigation Drawer
        val navController = (supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment?)
            ?.navController ?: return

        setupActionBar(navController)
        setupNavigationDrawer(navController)

    }

    override fun setDrawerEnable(enable: Boolean) {
        binding.drawerLayout.setDrawerLockMode(
            if (enable) androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
            else androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun setupActionBar(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
    }

    private fun setupNavigationDrawer(navController: NavController) {
        findViewById<NavigationView>(R.id.nav_view)?. let { navigationView ->
            NavigationUI.setupWithNavController(navigationView, navController)
        }
    }

    override fun onSupportNavigateUp()
            = NavigationUI.navigateUp(Navigation.findNavController(this, R.id.my_nav_host_fragment), binding.drawerLayout)

    override fun onOptionsItemSelected(item: MenuItem)
    // Have the NavHelper look for an action or destination matching the menu
    // item id and navigate there if found.
    // Otherwise, bubble up to the parent.
            = NavigationUI.onNavDestinationSelected(item,
        Navigation.findNavController(this, R.id.my_nav_host_fragment))
            || super.onOptionsItemSelected(item)

    override fun onBackPressed() {
        // catch back button to close Navigation drawer
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
