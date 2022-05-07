package com.example.storyapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.storyapp.CoroutineRule
import com.example.storyapp.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class StoryDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRule()


    private lateinit var database: StoryDatabase
    private lateinit var dao: StoryDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StoryDatabase::class.java
        ).build()
        dao = database.storyDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun inputStory() = mainCoroutineRule.runBlockingTest {
        val expectedStories = DataDummy.generateDummyNewStories()

        dao.insertStory(expectedStories)
        val actualStories = dao.getAllListStories()

        assertNotNull(actualStories)
        assertEquals(expectedStories, actualStories)
    }

    @Test
    fun deleteAllStories() = mainCoroutineRule.runBlockingTest {
        val dummy = DataDummy.generateDummyNewStories()
        dao.insertStory(dummy)
        dao.deleteAll()

        val actualStories = dao.getAllListStories()

        assertTrue(actualStories.isEmpty())
        assertFalse(actualStories.contains(dummy[0]))
    }
}