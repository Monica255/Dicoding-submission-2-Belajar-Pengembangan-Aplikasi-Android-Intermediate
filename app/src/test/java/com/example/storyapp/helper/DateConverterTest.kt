package com.example.storyapp.helper

import android.content.Context
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DateConverterTest{
    private lateinit var date: String
    private lateinit var dateConverter: DateConverter.Companion

    @Before
    fun setUp() {
        dateConverter = Mockito.mock(DateConverter.Companion::class.java)
        date = "2022-04-26T04.16.03.747Z"
    }

    @Test
    fun `when year should return year and not null`() {
        val expectedYear = "2022"

        Mockito.`when`(dateConverter.year(date)).thenReturn(expectedYear)

        val actualYear = dateConverter.year(date)

        Mockito.verify(dateConverter).year(date)
        assertNotNull(actualYear)
        assertEquals(expectedYear, actualYear)
    }

    @Test
    fun `when month should return month and not null`() {
        val expectedMonth = "04"

        Mockito.`when`(dateConverter.mouth(date)).thenReturn(expectedMonth)

        val actualMonth = dateConverter.mouth(date)

        Mockito.verify(dateConverter).mouth(date)
        assertNotNull(actualMonth)
        assertEquals(expectedMonth, actualMonth)
    }

    @Test
    fun `when day should return day and not null`() {
        val expectedDay = "26"

        Mockito.`when`(dateConverter.day(date)).thenReturn(expectedDay)

        val actualDay = dateConverter.day(date)

        Mockito.verify(dateConverter).day(date)
        assertNotNull(actualDay)
        assertEquals(expectedDay, actualDay)
    }

}