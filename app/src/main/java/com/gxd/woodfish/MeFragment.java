package com.gxd.woodfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import razerdp.basepopup.BasePopupWindow;

@SuppressLint("SetTextI18n")
public class MeFragment extends Fragment {

    public View rootView;
    private AppCompatActivity activity;
    private AppCompatTextView tvName;
    private AppCompatTextView tvTotal;
    private AppCompatImageView ivHead;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (rootView == null) {
            rootView = onInitloadView(inflater, container);
            initView(rootView);
        }
        return rootView;
    }

    private View onInitloadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    public void refreshNum() {
        int num = SPUtils.getInstance().getInt(SpConstants.TOTAL_NUM);
        if (null != tvTotal) {
            tvTotal.setText("Total：" + num);
        }
    }

    private void initView(View view) {
        ivHead = view.findViewById(R.id.iv_head);
        tvTotal = view.findViewById(R.id.tv_num);
        tvName = view.findViewById(R.id.tv_nickname);
        AppCompatTextView tvChange = view.findViewById(R.id.tv_change);
        AppCompatTextView tvClear = view.findViewById(R.id.tv_clear);
        AppCompatTextView tvSetNickname = view.findViewById(R.id.tv_set_nickname);
        AppCompatTextView tvExit = view.findViewById(R.id.tv_exit);

        String name = "Wisher";
        if (SPUtils.getInstance().contains(SpConstants.NICK_NAME)) {
            name = SPUtils.getInstance().getString(SpConstants.NICK_NAME);
        }
        tvName.setText(name);

        int color = R.color.color_f1e6d4;
        if (SPUtils.getInstance().contains(SpConstants.WOOD_FISH_COLOR)) {
            color = SPUtils.getInstance().getInt(SpConstants.WOOD_FISH_COLOR);
        }
        ivHead.setColorFilter(ColorUtils.getColor(color));

        refreshNum();

        tvChange.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChooseColorActivity.class);
            startActivity(intent);
        });

        tvClear.setOnClickListener(v -> {
            TipsPop pop = new TipsPop(activity, "Are you sure you want to clear it?", () -> {
                tvTotal.setText("Total：0");
                if (SPUtils.getInstance().contains(SpConstants.TOTAL_NUM)) {
                    SPUtils.getInstance().remove(SpConstants.TOTAL_NUM);
                }
                EventBus.getDefault().post(SpConstants.BUS_CONSTANTS_REFRESH_NUM);
            });
            if (!pop.isShowing()) {
                pop.showPopupWindow();
            }
        });

        tvSetNickname.setOnClickListener(v -> {
            ChangeNamePop pop = new ChangeNamePop(activity, name1 -> tvName.setText(name1));
            pop.setAutoShowKeyboard(pop.findViewById(R.id.et_new_name), true);
            pop.setKeyboardGravity(Gravity.BOTTOM);
            pop.setKeyboardAdaptionMode(R.id.et_new_name, BasePopupWindow.FLAG_KEYBOARD_ALIGN_TO_ROOT | BasePopupWindow.FLAG_KEYBOARD_ANIMATE_ALIGN);
            if (!pop.isShowing()) {
                pop.showPopupWindow();
            }
        });

        tvExit.setOnClickListener(v -> {
            TipsPop pop = new TipsPop(activity, "Are you sure you want to exit?", () -> activity.finish());
            if (!pop.isShowing()) {
                pop.showPopupWindow();
            }
        });

        view.findViewById(R.id.tv_about).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        });
    }

    @Subscribe
    public void onBus(Bus bus) {
        if (bus != null) {
            if (Objects.equals(bus.type, SpConstants.BUS_CONSTANTS_COLOR)) {
                int color = (int) bus.data;
                ivHead.setColorFilter(ColorUtils.getColor(color));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
