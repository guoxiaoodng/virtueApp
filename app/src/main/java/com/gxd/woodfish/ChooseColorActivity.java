package com.gxd.woodfish;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ChooseColorActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bg);

        AppCompatImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        RecyclerView rv = findViewById(R.id.rv);

        List<ColorBean> list = new ArrayList<>();
        list.add(new ColorBean("Default", R.color.color_f1e6d4));
        list.add(new ColorBean("Green", R.color.teal_200));
        list.add(new ColorBean("Purple", R.color.purple_200));
        list.add(new ColorBean("Blue", R.color.color_1677ff));
        list.add(new ColorBean("Red", R.color.red_fb1b5e));

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        ColorTintAdapter adapter = new ColorTintAdapter();
        adapter.setNewData(list);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            ColorBean bean = (ColorBean) adapter1.getItem(position);
            EventBus.getDefault().post(new Bus(SpConstants.BUS_CONSTANTS_COLOR, bean.getColorTint()));
            SPUtils.getInstance().put(SpConstants.WOOD_FISH_COLOR, bean.getColorTint());
            finish();
        });
    }
}
