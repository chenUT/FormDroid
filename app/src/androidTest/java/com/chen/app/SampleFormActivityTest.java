package com.chen.app;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import android.widget.Button;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by chen on 7/5/15.
 */
public class SampleFormActivityTest extends ActivityInstrumentationTestCase2<SampleFormActivity> {

    private SampleFormActivity mActivity;

    public SampleFormActivityTest(){
       super(SampleFormActivity.class);
    }

    public SampleFormActivityTest(Class<SampleFormActivity> activityClass) {
        super(activityClass);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testFragmentShowUp_sameActivity(){
        //check framelayout is displayed
        ViewInteraction fragmentText = onView(withId(R.id.frame));
        fragmentText.check((ViewAssertions.matches(isDisplayed())));
    }

    public void testTextInputShowUp_sameActivity(){
        ViewInteraction textInput = onView(withText("text input"));
        textInput.check((ViewAssertions.matches(isDisplayed())));
    }

    public void testCheckboxShowUp_sameActivity(){
        ViewInteraction checkboxInput = onView(withText("check box input"));
        checkboxInput.check((ViewAssertions.matches(isDisplayed())));
    }

    public void testDialogShowUp_sameActivity(){
        ViewInteraction dialogInput = onView(withText("New dialog"));
        dialogInput.check((ViewAssertions.matches(isDisplayed())));
    }

    public void testDeleteAndLoadFormView_sameActivity(){
        //remove fragment view
        onView(withId(R.id.delete)).perform(click());

        //check all fields are removed
        ViewInteraction textInput = onView(withText("text input"));
        textInput.check((ViewAssertions.doesNotExist()));

        ViewInteraction checkboxInput = onView(withText("check box input"));
        checkboxInput.check(ViewAssertions.doesNotExist());

        ViewInteraction dialogInput = onView(withText("New dialog"));
        dialogInput.check((ViewAssertions.doesNotExist()));

        //load it back
        onView(withId(R.id.load)).perform(click());

        //check everything is back
        textInput = onView(withText("text input"));
        textInput.check((ViewAssertions.matches(isDisplayed())));

        checkboxInput = onView(withText("check box input"));
        checkboxInput.check((ViewAssertions.matches(isDisplayed())));

        dialogInput = onView(withText("New dialog"));
        dialogInput.check((ViewAssertions.matches(isDisplayed())));
    }

    //TODO functional test on three types of views
    public void testTextInputFunction_sameActivity(){

    }

    public void testCheckBoxInputFunction_sameActivity(){

    }

    public void textDialogInputFunction_sameActivity(){

    }

    public void tearDown() throws Exception {
        Log.d("TEARDOWN", "TEARDOWN");
        super.tearDown();
    }
}
