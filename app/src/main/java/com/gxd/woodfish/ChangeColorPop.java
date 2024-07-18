package com.gxd.woodfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;

import razerdp.basepopup.BasePopupWindow;

public class ChangeColorPop extends BasePopupWindow {

    private RadioGroup radioGroup;

    public ChangeColorPop(Context context) {
        super(context);
        setContentView(R.layout.pop_choose_color);
        setPopupGravity(Gravity.BOTTOM);
        setAlignBackground(true);
        setOutSideDismiss(true);

        initView();
    }

    @SuppressLint("NonConstantResourceId")
    private void initView() {
        radioGroup = findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int color;
                switch (checkedId) {
                    case R.id.rb_green:
                        color = R.color.teal_200;
                        break;
                    case R.id.rb_purple:
                        color = R.color.purple_200;
                        break;
                    case R.id.rb_blue:
                        color = R.color.color_1677ff;
                        break;
                    case R.id.rb_red:
                        color = R.color.red_fb1b5e;
                        break;
                    default:
                        color = R.color.color_f1e6d4;
                        break;
                }
                EventBus.getDefault().post(new Bus(SpConstants.BUS_CONSTANTS_COLOR, color));
                dismiss();
            }
        });
    }
}
