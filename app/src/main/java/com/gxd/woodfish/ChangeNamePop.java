package com.gxd.woodfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import razerdp.basepopup.BasePopupWindow;

public class ChangeNamePop extends BasePopupWindow {

    private AppCompatEditText etText;
    private AppCompatTextView tvSure, tvCancel;
    private Context mContext;

    private OnSaveListener mListener;

    public ChangeNamePop(Context context, OnSaveListener listener) {
        super(context);
        mContext = context;
        this.mListener = listener;
        setContentView(R.layout.pop_change_nickname);
        setPopupGravity(Gravity.CENTER);
        setWidth(ScreenUtils.getScreenWidth() - SizeUtils.dp2px(40));
        setAlignBackground(true);
        setOutSideDismiss(true);

        initView();
    }

    @SuppressLint("NonConstantResourceId")
    private void initView() {
        etText = findViewById(R.id.et_new_name);
        tvSure = findViewById(R.id.tv_sure);
        tvCancel = findViewById(R.id.tv_cancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = String.valueOf(etText.getText());
                if (StringUtils.isEmpty(newName)) {
                    Toast.makeText(mContext, "Please input new nickname", Toast.LENGTH_SHORT).show();
                    return;
                }
                SPUtils.getInstance().put(SpConstants.NICK_NAME, newName);
                if (mListener != null) {
                    mListener.onSaveListener(newName);
                }
                dismiss();
            }
        });
    }

    public interface OnSaveListener {
        void onSaveListener(String name);
    }
}
