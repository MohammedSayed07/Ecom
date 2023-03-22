package com.example.ecom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ecom.databinding.ActivityMainBinding
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
        val navigationBarView = binding.navView

        navigationBarView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    var current = navController.backQueue.lastOrNull()
                    if(current?.destination?.id == R.id.productDetailsFragment){
                        navController.popBackStack()
                    }
                    while(current != null){
                        if(current.destination.id != R.id.productDetailsFragment && current.destination.id != R.id.navigation_home) {
                            navController.popBackStack()
                            current = navController.backQueue.lastOrNull()
                        } else{
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