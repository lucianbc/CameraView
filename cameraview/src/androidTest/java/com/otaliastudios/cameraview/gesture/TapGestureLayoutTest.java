package com.otaliastudios.cameraview.gesture;


import android.content.Context;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.otaliastudios.cameraview.gesture.Gesture;
import com.otaliastudios.cameraview.gesture.GestureLayoutTest;
import com.otaliastudios.cameraview.gesture.TapGestureLayout;
import com.otaliastudios.cameraview.size.Size;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TapGestureLayoutTest extends GestureLayoutTest<TapGestureLayout> {

    @Override
    protected TapGestureLayout create(Context context) {
        return new TapGestureLayout(context);
    }

    @Test
    public void testDefaults() {
        assertNull(layout.getGesture());
        assertEquals(layout.getPoints().length, 1);
        assertEquals(layout.getPoints()[0].x, 0, 0);
        assertEquals(layout.getPoints()[0].y, 0, 0);
    }

    @Test
    public void testTap() {
        touch.listen();
        touch.start();
        GeneralClickAction a = new GeneralClickAction(
                Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER,
                InputDevice.SOURCE_UNKNOWN, MotionEvent.BUTTON_PRIMARY);
        onLayout().perform(a);
        Gesture found = touch.await(500);

        assertEquals(found, Gesture.TAP);
        Size size = rule.getActivity().getContentSize();
        assertEquals(layout.getPoints()[0].x, (size.getWidth() / 2f), 1f);
        assertEquals(layout.getPoints()[0].y, (size.getHeight() / 2f), 1f);
    }

    @Test
    public void testTapWhileDisabled() {
        layout.setActive(false);
        touch.listen();
        touch.start();
        onLayout().perform(click());
        Gesture found = touch.await(500);
        assertNull(found);
    }

    @Test
    public void testLongTap() {
        touch.listen();
        touch.start();
        GeneralClickAction a = new GeneralClickAction(
                Tap.LONG, GeneralLocation.CENTER, Press.FINGER,
                InputDevice.SOURCE_UNKNOWN, MotionEvent.BUTTON_PRIMARY);
        onLayout().perform(a);
        Gesture found = touch.await(500);
        assertEquals(found, Gesture.LONG_TAP);
        Size size = rule.getActivity().getContentSize();
        assertEquals(layout.getPoints()[0].x, (size.getWidth() / 2f), 1f);
        assertEquals(layout.getPoints()[0].y, (size.getHeight() / 2f), 1f);
    }
}
