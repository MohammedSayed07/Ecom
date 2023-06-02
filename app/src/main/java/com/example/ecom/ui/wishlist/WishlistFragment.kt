package com.example.ecom.ui.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecom.SharedPreferencesManager
import com.example.ecom.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null

    private val viewModel: WishlistViewModel by viewModels()

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        viewLifecycleOwner

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        val userId = sharedPreferencesManager.getUserId()

        viewModel.getCartProducts(userId)

        val adapter = WishlistAdapter(addToCart = {
            viewModel.addItemToCart(userId, it)
        })

        binding.recyclerView2.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect {
                    adapter.submitList(it)
                }
            }
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}