package com.example.storyapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.storyapp.CoroutineRule
import com.example.storyapp.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class RemoteKeysDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRule()


    private lateinit var database: StoryDatabase
    private lateinit var dao: RemoteKeysDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StoryDatabase::class.java
        ).build()
        dao = database.remoteKeysDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun inputRemoteKeys() = mainCoroutineRule.runBlockingTest {
        val expectedKeys = DataDummy.generateDummyRemoteKeys()

        dao.insertAll(expectedKeys)
        val actualRemoteKey = dao.getRemoteKeysId(expectedKeys[0].id)

        assertNotNull(actualRemoteKey)
        assertEquals(expectedKeys[0], actualRemoteKey)
    }

    @Test
    fun deleteRemoteKeys() = mainCoroutineRule.runBlockingTest {
        val expectedKeys = DataDummy.generateDummyRemoteKeys()
        dao.insertAll(expectedKeys)
        dao.deleteRemoteKeys()

        val actualRemoteKey = dao.getRemoteKeysId(expectedKeys[0].id)

        assertNull(actualRemoteKey)
    }
}