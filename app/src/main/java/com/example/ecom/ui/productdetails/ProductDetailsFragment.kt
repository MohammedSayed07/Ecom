package com.example.ecom.ui.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.ecom.R
import com.example.ecom.databinding.FragmentProductDetailsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        val selectedProduct = ProductDetailsFragmentArgs.fromBundle(requireArguments()).product

        val viewModelFactory = ProductDetailsViewModelFactory(selectedProduct)

        val viewModel = ViewModelProvider(this, viewModelFactory)[ProductDetailsViewModel::class.java]

        binding.viewModel = viewModel

        binding.backButton.setOnClickListener {
            findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToNavigationHome())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}