package com.example.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.CoroutineRule
import com.example.storyapp.ListStoryPaging
import com.example.storyapp.adapter.ListStoryAdapter
import com.example.storyapp.DataDummy
import com.example.storyapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalPagingApi::class,ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @Mock
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = mock(HomeViewModel::class.java)
    }

    @Test
    fun `verify getStory is working and Should Not Return Null`() =
        mainCoroutineRule.runBlockingTest {
            val noopListUpdateCallback = NoopListCallback()
            val dummyStory = DataDummy.generateDummyNewStories()
            val data = PagedTestDataSources.snapshot(dummyStory)
            val story = MutableLiveData<PagingData<ListStoryPaging>>()
            val token = "ini token"
            story.value = data
            `when`(homeViewModel.getStories(token)).thenReturn(story)
            val actualData = homeViewModel.getStories(token).getOrAwaitValue()

            val differ = AsyncPagingDataDiffer(
                diffCallback = ListStoryAdapter.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                mainDispatcher = mainCoroutineRule.dispatcher,
                workerDispatcher = mainCoroutineRule.dispatcher,
            )
            differ.submitData(actualData)


            advanceUntilIdle()
            verify(homeViewModel).getStories(token)
            assertNotNull(differ.snapshot())
            assertEquals(dummyStory.size, differ.snapshot().size)
            assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)

        }


    //data empty

    @Test
    fun `when GetStory is Empty Should Not return Null`() = mainCoroutineRule.runBlockingTest {
        val noopListUpdateCallback = NoopListCallback()
        val data = PagedTestDataSources.snapshot(listOf())
        val story = MutableLiveData<PagingData<ListStoryPaging>>()
        val token = "ini token"
        story.value = data
        `when`(homeViewModel.getStories(token)).thenReturn(story)
        val actualData = homeViewModel.getStories(token).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher,
        )
        differ.submitData(actualData)


        advanceUntilIdle()
        verify(homeViewModel).getStories(token)
        assertNotNull(differ.snapshot())
        assertTrue(differ.snapshot().isEmpty())
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