package com.example.myapplication

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Test
    fun testNavigation() {
        launchActivity<MainActivity>()

        // we are on 1st screen
        isFragmentShown(R.id.fragment1)
        pressButton(R.id.bnToSecond)

        // we are on 2nd screen
        isFragmentShown(R.id.fragment2)
        pressButton(R.id.bnToThird)

        // we are on 3rd screen
        isFragmentShown(R.id.fragment3)
        pressButton(R.id.bnToFirst)

        // we are on 1st screen again
        isFragmentShown(R.id.fragment1)
        pressButton(R.id.bnToSecond)

        // we are on 2nd screen again
        isFragmentShown(R.id.fragment2)  // may be redundant
        pressButton(R.id.bnToFirst)

        // we are on 1st screen
        isFragmentShown(R.id.fragment1)

        pressButton(R.id.bnToSecond)
        isFragmentShown(R.id.fragment2)
        pressButton(R.id.bnToThird)
        isFragmentShown(R.id.fragment3)
        pressButton(R.id.bnToSecond)
        isFragmentShown(R.id.fragment2)
    }

    @Test
    fun testAbout() {
        launchActivity<MainActivity>()
        // we are on 1st screen
        openAbout()
        checkAboutActivity()
        pressBack()
        isFragmentShown(R.id.fragment1)
        pressButton(R.id.bnToSecond)

        // we are on 2nd screen
        openAbout()
        checkAboutActivity()
        pressBack()
        isFragmentShown(R.id.fragment2)
        pressButton(R.id.bnToThird)

        // we are on 3rd screen
        openAbout()
        checkAboutActivity()
        pressBack()
        isFragmentShown(R.id.fragment3)
    }

    @Test
    fun testBackstackSimple() {
        launchActivity<MainActivity>()

        goToSecondFromFirst()
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment1)

        goToThirdFromFirst()
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment2)
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment1)

        openAbout()
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment1)

        goToSecondFromFirst()
        openAbout()
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment2)
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment1)

        goToThirdFromFirst()
        openAbout()
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment3)
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment2)
        pressBackNTimes(1)
        isFragmentShown(R.id.fragment1)
    }

    @Test
    fun testRecreate() {
        val scenario = launchActivity<MainActivity>()

        // we are on 1st screen
        isFragmentShown(R.id.fragment1)
        rotateScreen(scenario)
        isFragmentShown(R.id.fragment1)
        pressButton(R.id.bnToSecond)

        // we are on 2nd screen
        isFragmentShown(R.id.fragment2)
        rotateScreen(scenario)
        isFragmentShown(R.id.fragment2)
        pressButton(R.id.bnToThird)

        // we are on 3rd screen
        isFragmentShown(R.id.fragment3)
        rotateScreen(scenario)
        isFragmentShown(R.id.fragment3)
        pressButton(R.id.bnToFirst)

        openAbout()
        checkAboutActivity()
        pressBackNTimes(1)
        rotateScreen(scenario)
        openAbout()
        checkAboutActivity()
        pressBack()

        openAbout()
        checkAboutActivity()
        rotateScreen(scenario)
        checkAboutActivity()
    }

    @Test
    fun testBackstackMaxSize() {
        var scenario = launchActivity<MainActivity>()
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        goToSecondFromFirst()
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        goToThirdFromFirst()
        scenario.checkBackstackDepth(3)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToSecond)
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToFirst)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToSecond)
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToFirst)
        pressButton(R.id.bnToSecond)
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToFirst)
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        scenario.checkBackstackDepth(3)
    }

    @Test
    fun testBackstackWithRotations() {
        var scenario = launchActivity<MainActivity>()
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        goToSecondFromFirst()
        rotateScreen(scenario)
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        goToThirdFromFirst()
        rotateScreen(scenario)
        scenario.checkBackstackDepth(3)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToSecond)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToFirst)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToSecond)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToFirst)
        pressButton(R.id.bnToSecond)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(2)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToFirst)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        pressButton(R.id.bnToFirst)
        pressButton(R.id.bnToSecond)
        pressButton(R.id.bnToThird)
        rotateScreen(scenario)
        scenario.checkBackstackDepth(3)
    }

    @Test
    fun testBackstackMaxSizeWithAbout() {
        var scenario = launchActivity<MainActivity>()
        openAbout()
        pressBackUnconditionallyNTimes(2)
        Assert.assertEquals(scenario.state, Lifecycle.State.DESTROYED)

        scenario = launchActivity()
        goToSecondFromFirst()
        openAbout()
        pressBackUnconditionallyNTimes(3)
        Assert.assertEquals(scenario.state, Lifecycle.State.DESTROYED)

        scenario = launchActivity()
        goToThirdFromFirst()
        openAbout()
        pressBackUnconditionallyNTimes(4)
        Assert.assertEquals(scenario.state, Lifecycle.State.DESTROYED)
    }

    @Test
    fun testBackWithUpNavigation() {
        var scenario = launchActivity<MainActivity>()
        goToSecondFromFirst()
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment1)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        goToThirdFromFirst()
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment2)
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment1)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        goToSecondFromFirst()
        openAbout()
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment2)
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment1)
        scenario.checkBackstackDepth(1)

        scenario = launchActivity()
        goToThirdFromFirst()
        openAbout()
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment3)
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment2)
        openAbout()
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment2)
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment1)
        openAbout()
        pressBackUpNav(1)
        isFragmentShown(R.id.fragment1)
        scenario.checkBackstackDepth(1)
    }
}