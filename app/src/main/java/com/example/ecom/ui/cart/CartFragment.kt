package com.example.ecom.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecom.R
import com.example.ecom.SharedPreferencesManager
import com.example.ecom.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    var count = 1
    private var _binding: FragmentCartBinding? = null

    private val viewModel: CartViewModel by viewModels()

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
                viewModel.backButtonPressed()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        val userId = sharedPreferencesManager.getUserId()
        viewModel.getCartProducts(userId)
        viewLifecycleOwner

        val cartAdapter = CartAdapter(decreaseClicked = {
            viewModel.decreaseQuantity(it, userId)
        }, increaseClicked = {
            viewModel.increaseQuantity(it, userId)

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

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.confirmPopBackStack.collectLatest {
                        Toast.makeText(requireActivity(), "Are you sure you want to close the app?", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.closeApp.collectLatest {
                    requireActivity().finish()
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