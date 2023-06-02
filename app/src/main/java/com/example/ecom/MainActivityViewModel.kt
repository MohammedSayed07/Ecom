package com.example.ecom

import androidx.lifecycle.ViewModel
import com.example.ecom.domain.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {
    val logoutReceiver = storeRepository.logoutChannel.receiveAsFlow()

}