package com.capstone.mentordeck.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.mentordeck.data.model.StoryModel
import com.capstone.mentordeck.data.model.UserModel
import com.capstone.mentordeck.data.response.StoryResponse
import com.capstone.mentordeck.data.retrofit.ApiConfig
import com.capstone.mentordeck.data.test.StoryEntity
import com.capstone.mentordeck.repository.Repository
import com.capstone.mentordeck.utils.UserPreferences
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val pref: UserPreferences): ViewModel() {

    private val _storyList = MutableLiveData<List<StoryModel>?>()
    val storyList : MutableLiveData<List<StoryModel>?> = _storyList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled : ${throwable.localizedMessage}")
    }


    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getAllStories(token : String) {
        _isLoading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val client = ApiConfig.getApiService().getStories("Bearer $token")
            withContext(Dispatchers.Main) {
                client.enqueue(object : Callback<StoryResponse> {
                    override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                _storyList.value = response.body()!!.stories
                                response.body()!!.message?.let {
                                    onError(it)
                                }
                            }
                        } else {
                            onError("Error : ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                        _isLoading.value = false
                        onError("onFailure")
                    }
                })
            }
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        _isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}