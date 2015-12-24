package epsz.mapalarm

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.test.ActivityInstrumentationTestCase2
import android.test.suitebuilder.annotation.LargeTest
import org.junit.Before

@LargeTest
class MapActivityTests : ActivityInstrumentationTestCase2<MapActivity>(MapActivity::class.java) {

    lateinit private var mapActivity: MapActivity

    @Before @Throws(Exception::class) public override fun setUp() {
        super.setUp()
        injectInstrumentation(InstrumentationRegistry.getInstrumentation())
        mapActivity = activity
    }

    public fun test_clickAddAlarmButton_entersEditMode() {
        clickViewWithId(R.id.add_alarm_button)

        onView(withId(R.id.toolbar_title))
                .check(matches(withTextId(R.string.instructions_add_mode)))
    }

    private fun withTextId(id: Int) = withText(mapActivity!!.getString(id))

    private fun clickViewWithId(id: Int) {
        onView(withId(id)).perform(click())
    }
}

