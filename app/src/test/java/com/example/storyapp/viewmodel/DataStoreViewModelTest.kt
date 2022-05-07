package com.example.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
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
class DataStoreViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataStoreViewModel: DataStoreViewModel

    private val token = "ini token"
    private val name = "ini nama"
    private val loginState = true

    @Before
    fun setUp() {
        dataStoreViewModel = mock(DataStoreViewModel::class.java)
    }

    @Test
    fun `when getLoginState return the right data and not null`() {
        val expectedLoginState = MutableLiveData<Boolean>()
        expectedLoginState.value = loginState

        `when`(dataStoreViewModel.getLoginState()).thenReturn(expectedLoginState)

        val actualLoginState = dataStoreViewModel.getLoginState().getOrAwaitValue()

        verify(dataStoreViewModel).getLoginState()
        assertNotNull(actualLoginState)
        assertEquals(expectedLoginState.value, actualLoginState)
    }

    @Test
    fun `verify saveLoginState function is working`() {
        dataStoreViewModel.saveLoginState(loginState)
        verify(dataStoreViewModel).saveLoginState(loginState)
    }

    @Test
    fun `when getToken return the right data and not null`() {
        val expectedToken = MutableLiveData<String>()
        expectedToken.value = token

        `when`(dataStoreViewModel.getToken()).thenReturn(expectedToken)

        val actualToken = dataStoreViewModel.getToken().getOrAwaitValue()

        verify(dataStoreViewModel).getToken()
        assertNotNull(actualToken)
        assertEquals(expectedToken.value, actualToken)
    }

    @Test
    fun `verify saveToken function is working`() {
        val token = "ini token"

        dataStoreViewModel.saveToken(token)
        verify(dataStoreViewModel).saveToken(token)
    }

    @Test
    fun `when getName return the right data and not null`() {
        val expectedName = MutableLiveData<String>()
        expectedName.value = name

        `when`(dataStoreViewModel.getName()).thenReturn(expectedName)

        val actualName = dataStoreViewModel.getName().getOrAwaitValue()

        verify(dataStoreViewModel).getName()
        assertNotNull(actualName)
        assertEquals(expectedName.value, actualName)
    }

    @Test
    fun `verify saveName function is working`() {
        dataStoreViewModel.saveName(name)
        verify(dataStoreViewModel).saveName(name)
    }

}