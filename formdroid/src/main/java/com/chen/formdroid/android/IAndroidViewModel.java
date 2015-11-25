package com.chen.formdroid.android;

import android.support.v4.app.Fragment;

import com.chen.formdroid.fdcore.internal.AbsInputFieldViewController;

/**
 * Created by chen on 11/25/15.
 */
public interface IAndroidViewModel {

    /**
     * get the view controller for this particular model
     * @param frag this is the fragment reference com from the attached fragment view
     * @return
     */
    AbsInputFieldViewController getViewController(Fragment frag);
}
