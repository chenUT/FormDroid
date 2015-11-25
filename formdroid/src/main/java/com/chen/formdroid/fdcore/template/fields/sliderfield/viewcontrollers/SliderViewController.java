package com.chen.formdroid.fdcore.template.fields.sliderfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chen.formdroid.R;
import com.chen.formdroid.fdcore.internal.AbsInputFieldViewController;
import com.chen.formdroid.fdcore.template.fields.sliderfield.models.SliderField;

/**
 * viewcontroller for slider view
 * Created by chen on 11/25/15.
 */
public class SliderViewController extends AbsInputFieldViewController<SliderField>{

    private SeekBar mSeekBar;
    private TextView mLabel;

    public SliderViewController(SliderField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    protected View getView(final SliderField field, Fragment frag) {
        LinearLayout wrapper = (LinearLayout)getInflater().inflate(R.layout.inputfield_slider, null, false);

        mLabel = (TextView)wrapper.findViewById(R.id.sliderfield_label);

        mSeekBar = (SeekBar)wrapper.findViewById(R.id.sliderfield_slider);

        initViewValue(field);

        return wrapper;
    }

    @Override
    protected void initViewValue(final SliderField field) {
        mLabel.setText(String.format("%s:  %s", field.getName(), getSlidingValueString()));
        mSeekBar.setMax(field.getMaxValue());
        mSeekBar.setProgress(field.getValue());
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    field.setValue(progress);
                    invalidateView(field);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /***private methods******/
    private String getSlidingValueString(){
        int max = this.getField().getMaxValue();
        int currValue = this.getField().getValue();
        return String.format("%s/%s", currValue, max);
    }
}
