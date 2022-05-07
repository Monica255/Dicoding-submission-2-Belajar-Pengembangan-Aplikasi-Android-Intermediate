package com.example.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
import com.example.storyapp.ListStory
import com.example.storyapp.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStories = DataDummy.generateDummyNewsEntity()

    @Before
    fun setUp() {
        mapsViewModel = mock(MapsViewModel::class.java)
    }


    @Test
    fun `when stories should return the right data and not null`() {
        val expectedStories = MutableLiveData<List<ListStory>>()
        expectedStories.value = dummyStories

        `when`(mapsViewModel.storiess).thenReturn(expectedStories)

        val actualStories = mapsViewModel.storiess.getOrAwaitValue()

        verify(mapsViewModel).storiess

        assertNotNull(actualStories)
        assertEquals(expectedStories.value, actualStories)
        assertEquals(dummyStories.size, actualStories.size)
    }


    @Test
    fun `when message should return the right data and not null`() {
        val expectedMessage = MutableLiveData<String>()
        expectedMessage.value = "Stories fetched Successfully"

        `when`(mapsViewModel.message).thenReturn(expectedMessage)

        val actualRegisterMessage = mapsViewModel.message.getOrAwaitValue()

        verify(mapsViewModel).message
        assertNotNull(actualRegisterMessage)
        assertEquals(expectedMessage.value, actualRegisterMessage)
    }

    @Test
    fun `when loading state should return the right data and not null`() {
        val expectedLoadingData = MutableLiveData<Boolean>()
        expectedLoadingData.value = true

        `when`(mapsViewModel.isLoading).thenReturn(expectedLoadingData)

        val actualLoading = mapsViewModel.isLoading.getOrAwaitValue()

        verify(mapsViewModel).isLoading
        assertNotNull(actualLoading)
        assertEquals(expectedLoadingData.value, actualLoading)
    }

    @Test
    fun `verify getStories function is working`() {
        val expectedStories = MutableLiveData<List<ListStory>>()
        expectedStories.value = dummyStories

        val token = "ini token"
        mapsViewModel.getStories(token)
        verify(mapsViewModel).getStories(token)

        `when`(mapsViewModel.storiess).thenReturn(expectedStories)

        val actualStories = mapsViewModel.storiess.getOrAwaitValue()

        verify(mapsViewModel).storiess

        assertNotNull(actualStories)
        assertEquals(expectedStories.value, actualStories)
        assertEquals(dummyStories.size, actualStories.size)
    }

    //data empty

    @Test
    fun `verify getStories empty should return empty and not null`() {
        val expectedStories = MutableLiveData<List<ListStory>>()
        expectedStories.value = listOf()

        val token = "ini token"
        mapsViewModel.getStories(token)
        verify(mapsViewModel).getStories(token)

        `when`(mapsViewModel.storiess).thenReturn(expectedStories)

        val actualStories = mapsViewModel.storiess.getOrAwaitValue()

        verify(mapsViewModel).storiess

        assertNotNull(actualStories)
        assertTrue(actualStories.isEmpty())
    }
}