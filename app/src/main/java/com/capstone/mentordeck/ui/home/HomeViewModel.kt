package com.capstone.mentordeck.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.mentordeck.data.model.UserModel
import com.capstone.mentordeck.data.test.StoryEntity
import com.capstone.mentordeck.repository.Repository
import com.capstone.mentordeck.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val pref: UserPreferences, private val repository: Repository): ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getAllStories(token: String): LiveData<PagingData<StoryEntity>> {
        return repository.getAllStories(token).cachedIn(viewModelScope)
    }

}