package com.example.ecom.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecom.R
import com.example.ecom.databinding.FragmentCartBinding
import com.example.ecom.ui.home.StoreApiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private val viewModel: CartViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        viewLifecycleOwner

        val cartAdapter = CartAdapter(decreaseClicked = {
            viewModel.decreaseQuantity(it)
        }, increaseClicked = {
            viewModel.increaseQuantity(it)

        })

        binding.cartRecycler.adapter = cartAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect {
                    cartAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.totalPrice.collect {
                    binding.textView13.text = String.format("%.2f",it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.status.collect {
                    when (it) {
                        CartStatus.LOADING -> {
                            binding.statusImageView.visibility = View.VISIBLE
                            binding.statusImageView.setImageResource(R.drawable.loading_animation)
                        }
                        CartStatus.ERROR -> {
                            binding.statusImageView.visibility = View.VISIBLE
                            binding.statusImageView.setImageResource(R.drawable.ic_connection_error)
                        }
                        CartStatus.DONE -> {
                            binding.statusImageView.visibility = View.GONE
                        }
                        else -> {}
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}