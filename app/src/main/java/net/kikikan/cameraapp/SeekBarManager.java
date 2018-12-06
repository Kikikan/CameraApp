package net.kikikan.cameraapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SeekBar;

public class SeekBarManager implements SeekBar.OnSeekBarChangeListener{

    private MainActivity mainActivity;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b)
            mainActivity.delayTW.setText(String.valueOf(i));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public SeekBarManager(MainActivity ac) {
        mainActivity = ac;

        mainActivity.delayTW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!mainActivity.delayTW.getText().toString().equals(""))
                    mainActivity.sb.setProgress(Integer.parseInt(mainActivity.delayTW.getText().toString()));
            }
        });
    }
}
