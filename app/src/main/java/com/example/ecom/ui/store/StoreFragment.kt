package com.example.ecom.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecom.R
import com.example.ecom.SharedPreferencesManager
import com.example.ecom.StoreNavigationDirections
import com.example.ecom.databinding.FragmentStoreBinding


class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private lateinit var navController: NavController
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        if (!sharedPreferencesManager.isLoggedIn()) {
            findNavController().navigate(StoreFragmentDirections.actionStoreFragmentToLoginFragment())
        }

        navController = Navigation.findNavController(requireView().findViewById(R.id.storeFragmentContainer))

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
                R.id.navigation_cart -> {
                    navController.navigate(StoreNavigationDirections.actionGlobalNavigationCart())
                    true
                }
                R.id.navigation_favorite -> {
                    navController.navigate(StoreNavigationDirections.actionGlobalWishlistFragment())
                    true
                }
                R.id.navigation_user -> {
                    navController.navigate(StoreNavigationDirections.actionGlobalNavigationUser())
                    true
                }
                else -> false
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}