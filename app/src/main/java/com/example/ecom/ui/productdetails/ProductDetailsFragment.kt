package com.example.ecom.ui.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.ecom.R
import com.example.ecom.databinding.FragmentProductDetailsBinding
import com.example.ecom.domain.models.CartItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    private val viewModel: ProductDetailsViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        val selectedProduct = ProductDetailsFragmentArgs.fromBundle(requireArguments()).product

        viewModel.setProduct(selectedProduct)

        binding.viewModel = viewModel

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addToCart.setOnClickListener {
            requireActivity().lifecycleScope.launch {
                viewModel.addItemToCart()
            }
            findNavController().popBackStack()
        }

        binding.heartButton.setOnClickListener {
            Toast.makeText(requireContext(), "Item added to the wish list", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}