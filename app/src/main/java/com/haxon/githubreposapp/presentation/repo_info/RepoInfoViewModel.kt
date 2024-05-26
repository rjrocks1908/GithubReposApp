package com.haxon.githubreposapp.presentation.repo_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haxon.githubreposapp.domain.repository.ContributorRepository
import com.haxon.githubreposapp.util.GeneralResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoInfoViewModel @Inject constructor(
    private val contributorRepository: ContributorRepository
) : ViewModel() {

    var state by mutableStateOf(RepoInfoState())

    fun onEvent(event: RepoInfoEvent) {
        when (event) {
            is RepoInfoEvent.LoadContributors -> {
                loadContributors(event.url)
            }
        }
    }

    private fun loadContributors(url: String) {
        viewModelScope.launch {
            contributorRepository.getContributors(url).collect { result ->
                when(result) {
                    is GeneralResponse.Error -> {
//                        state = state.copy(error = result.message ?: "")
                    }
                    is GeneralResponse.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is GeneralResponse.Success -> {
                        state = state.copy(contributors = result.data ?: emptyList())
                    }
                }
            }
        }
    }

}