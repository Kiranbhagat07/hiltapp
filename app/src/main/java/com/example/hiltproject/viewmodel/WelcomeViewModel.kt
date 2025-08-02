package com.example.hiltproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiltproject.remoteconfig.RemoteConfigHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val remoteConfigHelper: RemoteConfigHelper
) : ViewModel() {

    private val _welcomeMessage = MutableLiveData<String>()
    val welcomeMessage: LiveData<String> = _welcomeMessage

    fun loadRemoteConfig() {
        remoteConfigHelper.fetchAndActivate { success ->
            if (success) {
                _welcomeMessage.value = remoteConfigHelper.getWelcomeMessage()
            } else {
                _welcomeMessage.value = "Failed to load message."
            }
        }
    }
}
