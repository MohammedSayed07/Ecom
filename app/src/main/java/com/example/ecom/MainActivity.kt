package com.example.ecom

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ecom.databinding.ActivityMainBinding
import com.example.ecom.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.fragment)

        binding.navView.setupWithNavController(navController)

        val navigationBarView = binding.navView

        navigationBarView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    var current = navController.backQueue.lastOrNull()
                    if (current?.destination?.id == R.id.productDetailsFragment) {
                        navController.popBackStack()
                    }
                    while (current != null) {
                        if (current.destination.id != R.id.productDetailsFragment && current.destination.id != R.id.navigation_home) {
                            navController.popBackStack()
                            current = navController.backQueue.lastOrNull()
                        } else {
                            break
                        }
                    }
                    true
                }
                R.id.navigation_dashboard -> {
                    navController.navigate(MobileNavigationDirections.actionGlobalNavigationDashboard())
                    true
                }
                R.id.navigation_notifications -> {
                    navController.navigate(MobileNavigationDirections.actionGlobalNavigationNotifications())
                    true
                }
                else -> false
            }
        }

    }

}