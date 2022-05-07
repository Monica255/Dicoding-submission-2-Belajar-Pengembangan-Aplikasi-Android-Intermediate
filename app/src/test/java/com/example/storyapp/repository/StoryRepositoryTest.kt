package com.example.storyapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.*
import com.example.storyapp.adapter.ListStoryAdapter
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.io.File


class StoryRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Mock
    private var mockFile = File("fileName")

    @Before
    fun setUp() {
        storyRepository = mock(StoryRepository::class.java)
    }

    //Variables

    @Test
    fun `when stories should return the right data and not null`() {
        val dummyStories = DataDummy.generateDummyNewsEntity()
        val expectedStories = MutableLiveData<List<ListStory>>()
        expectedStories.value = dummyStories

        `when`(storyRepository.storiess).thenReturn(expectedStories)

        val actualStories = storyRepository.storiess.getOrAwaitValue()

        verify(storyRepository).storiess

        assertNotNull(actualStories)
        assertEquals(expectedStories.value, actualStories)
        assertEquals(dummyStories.size, actualStories.size)
    }

    @Test
    fun `when message should return the right data and not null`() {
        val expectedRegisterMessage = MutableLiveData<String>()
        expectedRegisterMessage.value = "Story Uploaded"

        `when`(storyRepository.message).thenReturn(expectedRegisterMessage)

        val actualRegisterMessage = storyRepository.message.getOrAwaitValue()

        verify(storyRepository).message
        assertNotNull(actualRegisterMessage)
        assertEquals(expectedRegisterMessage.value, actualRegisterMessage)
    }

    @Test
    fun `when loading state should return the right data and not null`() {
        val expectedLoadingData = MutableLiveData<Boolean>()
        expectedLoadingData.value = true

        `when`(storyRepository.isLoading).thenReturn(expectedLoadingData)

        val actualLoading = storyRepository.isLoading.getOrAwaitValue()

        verify(storyRepository).isLoading
        assertNotNull(actualLoading)
        assertEquals(expectedLoadingData.value, actualLoading)
    }

    @Test
    fun `when login should return the right login response and not null`() {
        val dummyResponselogin = DataDummy.generateDummyResponseLogin()

        val expectedLogin = MutableLiveData<ResponseLogin>()
        expectedLogin.value = dummyResponselogin

        `when`(storyRepository.userlogin).thenReturn(expectedLogin)

        val actualLoginResponse = storyRepository.userlogin.getOrAwaitValue()

        verify(storyRepository).userlogin
        assertNotNull(actualLoginResponse)
        assertEquals(expectedLogin.value, actualLoginResponse)
    }


    //Functions

    @Test
    fun `verify getResponseRegister function is working`() {
        val dummyRequestRegister = DataDummy.generateDummyRequestRegister()
        val expectedRegisterMessage = MutableLiveData<String>()
        expectedRegisterMessage.value = "User Created"

        storyRepository.getResponseRegister(dummyRequestRegister)

        verify(storyRepository).getResponseRegister(dummyRequestRegister)

        `when`(storyRepository.message).thenReturn(expectedRegisterMessage)

        val actualData = storyRepository.message.getOrAwaitValue()

        verify(storyRepository).message
        assertNotNull(actualData)
        assertEquals(expectedRegisterMessage.value, actualData)
    }

    @Test
    fun `verify getResponseLogin function is working`() {
        val dummyRequestLogin = DataDummy.generateDummyRequestLogin()
        val dummyResponseLogin = DataDummy.generateDummyResponseLogin()

        val expectedResponseLogin = MutableLiveData<ResponseLogin>()
        expectedResponseLogin.value = dummyResponseLogin

        storyRepository.getResponseLogin(dummyRequestLogin)

        verify(storyRepository).getResponseLogin(dummyRequestLogin)

        `when`(storyRepository.userlogin).thenReturn(expectedResponseLogin)

        val actualData = storyRepository.userlogin.getOrAwaitValue()

        verify(storyRepository).userlogin
        assertNotNull(actualData)
        assertEquals(expectedResponseLogin.value, actualData)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Test
    fun `verify getPagingStory function is working and should not null`() = mainCoroutineRule.runBlockingTest {
        val noopListUpdateCallback = NoopListCallback()
        val dummyStory = DataDummy.generateDummyNewStories()
        val data = PagedTestDataSources.snapshot(dummyStory)
        val story = MutableLiveData<PagingData<ListStoryPaging>>()
        val token = "ini token"
        story.value = data

        `when`(storyRepository.getPagingStories(token)).thenReturn(story)

        val actualData = storyRepository.getPagingStories(token).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher,
        )
        differ.submitData(actualData)


        advanceUntilIdle()
        verify(storyRepository).getPagingStories(token)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)

    }


    @Test
    fun `verify getStories function is working`() {
        val dummyStories = DataDummy.generateDummyNewsEntity()
        val expectedStories = MutableLiveData<List<ListStory>>()
        expectedStories.value = dummyStories

        val token = "ini token"
        storyRepository.getStories(token)
        verify(storyRepository).getStories(token)

        `when`(storyRepository.storiess).thenReturn(expectedStories)

        val actualStories = storyRepository.storiess.getOrAwaitValue()

        verify(storyRepository).storiess

        assertNotNull(actualStories)
        assertEquals(expectedStories.value, actualStories)
        assertEquals(dummyStories.size, actualStories.size)
    }

    @Test
    fun `verify upload function is working`() {
        val expectedRegisterMessage = MutableLiveData<String>()
        expectedRegisterMessage.value = "Story Uploaded"

        val requestImageFile = mockFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "fileName",
            requestImageFile
        )
        val description: RequestBody = "ini description".toRequestBody("text/plain".toMediaType())
        val token = "ini token"
        val latlng = LatLng(1.1, 1.1)

        storyRepository.upload(
            imageMultipart,
            description,
            token,
            latlng.latitude,
            latlng.longitude
        )

        verify(storyRepository).upload(
            imageMultipart,
            description,
            token,
            latlng.latitude,
            latlng.longitude
        )

        `when`(storyRepository.message).thenReturn(expectedRegisterMessage)

        val actualRegisterMessage = storyRepository.message.getOrAwaitValue()

        verify(storyRepository).message
        assertNotNull(actualRegisterMessage)
        assertEquals(expectedRegisterMessage.value, actualRegisterMessage)
    }


    class NoopListCallback : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }

    class PagedTestDataSources private constructor() :
        PagingSource<Int, LiveData<List<ListStoryPaging>>>() {
        companion object {
            fun snapshot(items: List<ListStoryPaging>): PagingData<ListStoryPaging> {
                return PagingData.from(items)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryPaging>>>): Int {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryPaging>>> {
            return LoadResult.Page(emptyList(), 0, 1)
        }
    }
}