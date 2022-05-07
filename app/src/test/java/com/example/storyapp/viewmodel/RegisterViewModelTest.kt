package com.example.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
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
class RegisterViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setUp() {
        registerViewModel = mock(RegisterViewModel::class.java)
    }


    @Test
    fun `when message should return the right data and not null`() {
        val expectedRegisterMessage = MutableLiveData<String>()
        expectedRegisterMessage.value = "User Created"

        `when`(registerViewModel.message).thenReturn(expectedRegisterMessage)

        val actualRegisterMessage = registerViewModel.message.getOrAwaitValue()

        verify(registerViewModel).message
        assertNotNull(actualRegisterMessage)
        assertEquals(expectedRegisterMessage.value, actualRegisterMessage)
    }

    @Test
    fun `when loading state should return the right data and not null`() {
        val expectedLoadingData = MutableLiveData<Boolean>()
        expectedLoadingData.value = true

        `when`(registerViewModel.isLoading).thenReturn(expectedLoadingData)

        val actualLoading = registerViewModel.isLoading.getOrAwaitValue()

        verify(registerViewModel).isLoading
        assertNotNull(actualLoading)
        assertEquals(expectedLoadingData.value, actualLoading)
    }

    @Test
    fun `verify getResponseRegister function is working`() {
        val dummyRequestRegister = DataDummy.generateDummyRequestRegister()
        val expectedRegisterMessage = MutableLiveData<String>()
        expectedRegisterMessage.value = "User Created"

        registerViewModel.getResponseRegister(dummyRequestRegister)

        verify(registerViewModel).getResponseRegister(dummyRequestRegister)

        `when`(registerViewModel.message).thenReturn(expectedRegisterMessage)

        val actualData = registerViewModel.message.getOrAwaitValue()

        verify(registerViewModel).message
        assertNotNull(actualData)
        assertEquals(expectedRegisterMessage.value, actualData)
    }

}