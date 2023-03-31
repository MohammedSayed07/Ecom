package com.example.ecom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.ecom.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val homeViewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = homeViewModel

        val categoryAdapter = CategoryAdapter()

        val popularProductsAdapter = PopularProductsAdapter(
            productClicked = {
                this.findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToProductDetailsFragment(it))
            })

        val allProductAdapter = PopularProductsAdapter(
            productClicked = {
                this.findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToProductDetailsFragment(it))
            })

        binding.saleProductsRecycler.adapter = allProductAdapter

        binding.popularProductsRecycler.adapter = popularProductsAdapter

        binding.categoryRecycler.adapter = categoryAdapter

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.categories
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                    categoryAdapter.submitList(it)
                }
            }
        }

        homeViewModel.categoryState.observe(viewLifecycleOwner, Observer {
            categoryAdapter.initialSelectedPosition = it
        })

        categoryAdapter.selectedCategory = {
            homeViewModel.getProducts(it)
        }
        categoryAdapter.selectedPosition = {
            homeViewModel.setCategoryState(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}