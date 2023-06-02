package com.example.ecom.ui.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.ecom.R
import com.example.ecom.SharedPreferencesManager
import com.example.ecom.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    private val viewModel: ProductDetailsViewModel by viewModels()

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        viewLifecycleOwner

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        val userId = sharedPreferencesManager.getUserId()

        val selectedProduct = ProductDetailsFragmentArgs.fromBundle(requireArguments()).product

        viewModel.setProduct(selectedProduct)

        binding.viewModel = viewModel

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addToCart.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addItemToCart(userId)
            }
            findNavController().popBackStack()
        }

        binding.heartButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addItemToWishlist(userId)
            }
            binding.heartButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0 ,0)
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addedToFavorite.collectLatest {
                    Toast.makeText(requireContext(), "Added To Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}