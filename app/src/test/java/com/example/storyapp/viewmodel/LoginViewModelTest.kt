package com.example.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.ResponseLogin
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
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginViewModel = mock(LoginViewModel::class.java)
    }

    @Test
    fun `when login message should return the right data and not null`() {
        val expectedLoginMessage = MutableLiveData<String>()
        expectedLoginMessage.value = "Login Successfully"

        `when`(loginViewModel.message).thenReturn(expectedLoginMessage)

        val actualMessage = loginViewModel.message.getOrAwaitValue()

        verify(loginViewModel).message
        assertNotNull(actualMessage)
        assertEquals(expectedLoginMessage.value, actualMessage)
    }

    @Test
    fun `when loading state should return the right data and not null`() {
        val expectedLoadingData = MutableLiveData<Boolean>()
        expectedLoadingData.value = true

        `when`(loginViewModel.isLoading).thenReturn(expectedLoadingData)

        val actualLoading = loginViewModel.isLoading.getOrAwaitValue()

        verify(loginViewModel).isLoading
        assertNotNull(actualLoading)
        assertEquals(expectedLoadingData.value, actualLoading)
    }

    @Test
    fun `when login should return the right login user data and not null`() {
        val dummyResponselogin = DataDummy.generateDummyResponseLogin()

        val expectedLogin = MutableLiveData<ResponseLogin>()
        expectedLogin.value = dummyResponselogin

        `when`(loginViewModel.userlogin).thenReturn(expectedLogin)

        val actualLoginResponse = loginViewModel.userlogin.getOrAwaitValue()

        verify(loginViewModel).userlogin
        assertNotNull(actualLoginResponse)
        assertEquals(expectedLogin.value, actualLoginResponse)
    }

    @Test
    fun `verify getResponseLogin function is working`() {
        val dummyRequestLogin = DataDummy.generateDummyRequestLogin()
        val dummyResponseLogin = DataDummy.generateDummyResponseLogin()

        val expectedResponseLogin = MutableLiveData<ResponseLogin>()
        expectedResponseLogin.value = dummyResponseLogin

        loginViewModel.getResponseLogin(dummyRequestLogin)

        verify(loginViewModel).getResponseLogin(dummyRequestLogin)

        `when`(loginViewModel.userlogin).thenReturn(expectedResponseLogin)

        val actualData = loginViewModel.userlogin.getOrAwaitValue()

        verify(loginViewModel).userlogin
        assertNotNull(expectedResponseLogin)
        assertEquals(expectedResponseLogin.value, actualData)
    }

}

