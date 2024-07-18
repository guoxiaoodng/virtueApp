package com.gxd.woodfish;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ColorUtils;

import java.util.ArrayList;

public class ColorTintAdapter extends BaseQuickAdapter<ColorBean, BaseViewHolder> {

    public ColorTintAdapter() {
        super(R.layout.item_color, new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ColorBean item) {
        View convertView = helper.itemView;
        AppCompatImageView ivColor = convertView.findViewById(R.id.iv);
        AppCompatTextView tvColor = convertView.findViewById(R.id.tv_name);

        ivColor.setColorFilter(ColorUtils.getColor(item.getColorTint()));
        tvColor.setText(item.getColorName());
    }
}
