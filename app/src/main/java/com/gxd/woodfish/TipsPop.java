package com.gxd.woodfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import razerdp.basepopup.BasePopupWindow;

public class TipsPop extends BasePopupWindow {

    private final String mTips;
    private final OnTipsListener mListener;

    public TipsPop(Context context, String tips, OnTipsListener listener) {
        super(context);
        this.mTips = tips;
        this.mListener = listener;
        setContentView(R.layout.pop_tips);
        setPopupGravity(Gravity.CENTER);
        setAlignBackground(true);
        setOutSideDismiss(false);
        initView();
    }

    @SuppressLint("NonConstantResourceId")
    private void initView() {
        AppCompatTextView tvTips = findViewById(R.id.tv_tips);
        AppCompatTextView tvSure = findViewById(R.id.tv_sure);
        AppCompatTextView tvCancel = findViewById(R.id.tv_cancel);
        tvTips.setText(mTips);
        tvCancel.setOnClickListener(v -> dismiss());
        tvSure.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onSureListener();
            }
            dismiss();
        });
    }

    public interface OnTipsListener {
        void onSureListener();
    }
}
