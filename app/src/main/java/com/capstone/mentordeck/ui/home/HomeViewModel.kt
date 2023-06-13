package com.capstone.mentordeck.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.mentordeck.UiState
import com.capstone.mentordeck.database.MentorEntity
import com.capstone.mentordeck.dummy.DummyData
import com.capstone.mentordeck.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<MentorEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getAllMentor().collect { mentor ->
//                when (mentor.isEmpty()) {
//                    true -> repository.insertAllMentor(DummyData.mentor)
//                    else -> _uiState.value = UiState.Success(mentor)
//                }
//            }
//        }
//    }

    fun getAllMentor() = repository.getAllMentor()
}