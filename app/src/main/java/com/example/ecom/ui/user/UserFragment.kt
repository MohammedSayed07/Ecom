package com.example.ecom.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ecom.SharedPreferencesManager
import com.example.ecom.databinding.FragmentUserBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val viewModel: UserViewModel by viewModels()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        val userId = sharedPreferencesManager.getUserId()
        viewModel.getUser(userId)
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.userEmailTextView.text = it
        })

        binding.signOut.setOnClickListener {
            sharedPreferencesManager.clear()
            viewModel.logout()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}