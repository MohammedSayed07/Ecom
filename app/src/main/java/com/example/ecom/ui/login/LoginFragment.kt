package com.example.ecom.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecom.SharedPreferencesManager
import com.example.ecom.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private  var _binding: FragmentLoginBinding? = null

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val viewModel: LoginViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        binding.loginButton.setOnClickListener {

            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()

            lifecycleScope.launch {
                val users = viewModel.getAllUsers()
                var isFound = false
                for (user in users) {
                    if (user.email == email && user.password == password) {
                        isFound = true
                        sharedPreferencesManager.setLoggedIn(true)
                        sharedPreferencesManager.storeUserId(user.userId)
                        Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToStoreFragment())
                        break
                    }
                }
                if (!isFound) {
                    Toast.makeText(requireContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.signup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}