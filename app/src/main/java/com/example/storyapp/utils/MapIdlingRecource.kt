package com.example.storyapp.utils

import androidx.test.espresso.IdlingResource
import com.example.storyapp.ui.MapsLocationActivity

class MapIdlingRecource:IdlingResource {

    private var callback: IdlingResource.ResourceCallback?=null

    override fun getName(): String {
        return MapIdlingRecource::class.java.simpleName
    }

    override fun isIdleNow(): Boolean {
        val isIdle = MapsLocationActivity.IRcounter==0

        if(isIdle){
            callback?.onTransitionToIdle()
        }

        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback=callback
    }
}