package com.example.storyapp.helper

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class LocationConverterTest {

    private lateinit var latLng: LatLng

    @Mock
    private lateinit var locationConverter: LocationConverter.Companion

    @Mock
    private lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        locationConverter = mock(LocationConverter.Companion::class.java)
        instrumentationContext = mock(Context::class.java)
        latLng = LatLng(1.1, 1.1)
    }

    @Test
    fun `when lat and lng not null, should not return null`() {
        val lat = 1.1
        val lng = 1.1
        val expectedLatlng = latLng

        `when`(locationConverter.toLatlng(lat, lng)).thenReturn(expectedLatlng)

        val actualLatLng = locationConverter.toLatlng(lat, lng)

        verify(locationConverter).toLatlng(lat, lng)
        assertNotNull(actualLatLng)
        assertEquals(expectedLatlng, actualLatLng)
    }

    @Test
    fun `when lat and lng null should return null`() {
        `when`(locationConverter.toLatlng(null, null)).thenReturn(null)
        val actualLatLng = locationConverter.toLatlng(null, null)

        verify(locationConverter).toLatlng(null, null)
        assertNull(actualLatLng)
    }

    @Test
    fun `when address should return the right data and not null`() {
        val expectedStringAddress = "ini alamat"

        `when`(locationConverter.getStringAddress(latLng, instrumentationContext)).thenReturn(
            expectedStringAddress
        )

        val actualLatLng = locationConverter.getStringAddress(latLng, instrumentationContext)

        verify(locationConverter).getStringAddress(latLng, instrumentationContext)
        assertNotNull(actualLatLng)
        assertEquals(expectedStringAddress, actualLatLng)
    }

    @Test
    fun `when latlng null, address should not return null`() {
        val expectedStringAddress = "tidak ada alamat"

        `when`(locationConverter.getStringAddress(null, instrumentationContext)).thenReturn(
            expectedStringAddress
        )

        val actualLatLng = locationConverter.getStringAddress(null, instrumentationContext)

        verify(locationConverter).getStringAddress(null, instrumentationContext)
        assertNotNull(actualLatLng)
        assertEquals(expectedStringAddress, actualLatLng)
    }
}