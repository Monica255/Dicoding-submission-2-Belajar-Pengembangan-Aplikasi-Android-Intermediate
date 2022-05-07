package com.example.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.ListStory
import com.example.storyapp.repository.StoryRepository

class MapsViewModel(private val provideRepository: StoryRepository) : ViewModel() {

    var storiess: LiveData<List<ListStory>> = provideRepository.storiess

    val message: LiveData<String> = provideRepository.message

    val isLoading: LiveData<Boolean> = provideRepository.isLoading

    fun getStories(token: String) {
        provideRepository.getStories(token)
    }

}