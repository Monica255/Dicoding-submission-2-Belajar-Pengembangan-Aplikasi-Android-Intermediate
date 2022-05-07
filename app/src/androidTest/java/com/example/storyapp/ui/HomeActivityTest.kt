package com.example.storyapp.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.storyapp.R
import com.example.storyapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {


    @get:Rule
    val rule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadStories() {
        onView(withId(R.id.rv_stories))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_stories)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadDetailStory() {
        onView(withId(R.id.rv_stories)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        intended(hasComponent(DetailActivity::class.java.name))
        onView(withId(R.id.img_photo)).check(matches(isDisplayed()))
        onView(withId(R.id.et_des)).check(matches(isDisplayed()))
    }

    @Test
    fun loadAddStory() {
        onView(withId(R.id.fab)).perform(click())
        intended(hasComponent(AddActivity::class.java.name))

        onView(withId(R.id.img_photo)).check(matches(isDisplayed()))
        onView(withId(R.id.et_des)).check(matches(isDisplayed()))
        onView(withId(R.id.ll_location)).perform(click())
        intended(hasComponent(MapsLocationActivity::class.java.name))


        if(MapsLocationActivity.currentLagLng!=null){
            onView(withId(R.id.bt_current_location)).perform(click())
        }else{
            onView(withId(R.id.map)).check(matches(isDisplayed())).perform(click())
            onView(withId(R.id.bt_place_picker)).perform(click())
        }


        onView(withText(R.string.use_this_location)).check(matches(isDisplayed()))
        onView(withText(R.string.yes)).perform(click())
        intended(hasComponent(AddActivity::class.java.name))
    }

    @Test
    fun loadMaps() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.see_on_map)).perform(click())
        intended(hasComponent(MapsActivity::class.java.name))
        /*val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)*/

        onView(withId(R.id.rv_maps)).check(matches(isDisplayed()))
        onView(withId(R.id.map)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_normal)).perform(click())
        onView(withId(R.id.fab_satellite)).perform(click())
        onView(withId(R.id.fab_terrain)).perform(click())
        onView(withId(R.id.fab_hybrid)).perform(click())

        pressBack()
    }

    @Test
    fun dialogLogout() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.logout)).perform(click())
        onView(withText(R.string.you_sure)).check(matches(isDisplayed()))
    }

}
