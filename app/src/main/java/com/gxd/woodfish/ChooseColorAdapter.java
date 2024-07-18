package com.gxd.woodfish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ChooseColorAdapter extends BaseQuickAdapter<Integer, BaseViewHolder>{

    public ChooseColorAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {

    }
}
