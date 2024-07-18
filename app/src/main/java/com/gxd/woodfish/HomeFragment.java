package com.gxd.woodfish;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.VibrateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    public View rootView;
    private ImageView d_onclick;
    private int tab = 0;
    private Timer timer; //时钟对象
    private TextView tvTime;
    private TextView tvText;
    private static AnimatorSet animatorSet;
    private TextView tvTotal;
    private AppCompatTextView tvTips;
    private SparseArray<Integer> knockWoodenFishVoiceIds = new SparseArray<>();
    private SoundPool soundPool;
    private AppCompatActivity activity;

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
            rootView = onInitloadView(inflater, container, savedInstanceState);
            initView(rootView);
        }
        return rootView;
    }

    private View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void refreshNum() {
        if (SPUtils.getInstance().contains(SpConstants.TOTAL_NUM)) {
            tab = SPUtils.getInstance().getInt(SpConstants.TOTAL_NUM);
        } else {
            tab = 0;
        }
    }

    private void initView(View view) {
        knockWoodenFishVoiceIds.append(R.raw.knock_wooden_fish, 0);
        knockWoodenFishVoiceIds.append(R.raw.knock_wooden_fish_2, 0);
        animatorSet = new AnimatorSet();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(knockWoodenFishVoiceIds.size())
                .setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).build())
                .build();
        d_onclick = view.findViewById(R.id.d_onclick);
        tvTime = view.findViewById(R.id.tv_time);
        tvTotal = view.findViewById(R.id.tv_total);
        tvTotal.setVisibility(View.GONE);
        tvText = view.findViewById(R.id.meritsPlusOne);
        tvTips = view.findViewById(R.id.tv_tips);
        tvTips.setVisibility(View.VISIBLE);
        int color = R.color.color_f1e6d4;
        if (SPUtils.getInstance().contains(SpConstants.WOOD_FISH_COLOR)) {
            color = SPUtils.getInstance().getInt(SpConstants.WOOD_FISH_COLOR);
        }
        d_onclick.setColorFilter(ColorUtils.getColor(color));
        d_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTips.getVisibility() == View.VISIBLE) {
                    tvTips.setVisibility(View.GONE);
                }
                VibrateUtils.vibrate(300);
                startScaleOutAnimD(activity, d_onclick);
                startScaleInAnimD(activity, d_onclick);

                tab = tab + 1;
                tvTotal.setText("Merits Total：" + tab);
                SPUtils.getInstance().put(SpConstants.TOTAL_NUM, tab);

                startTextAnimate(tvText);

                playKnockVoice(R.raw.knock_wooden_fish);
            }
        });
        refreshNum();
        tvTotal.setText("Merits Total：" + tab);

        time();
    }

    private void playKnockVoice(int resId) {
        if (soundPool == null) {
            Toast.makeText(activity, "Application not init", Toast.LENGTH_SHORT).show();
            return;
        } else if (resId <= 0) {
            Toast.makeText(activity, "Resource Error", Toast.LENGTH_SHORT).show();
            return;
        } else if (knockWoodenFishVoiceIds.get(resId) == 0) {
            soundPool.load(activity, resId, 1);
            soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
                if (status == 0) {
                    knockWoodenFishVoiceIds.put(resId, sampleId);
                    soundPool.play(knockWoodenFishVoiceIds.get(resId), 1f, 1f, 1, 0, 1f);
                }
            });
            return;
        }
        soundPool.play(knockWoodenFishVoiceIds.get(resId), 1f, 1f, 1, 0, 1f);
    }

    private void time() {
        if (timer == null) {
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                //这里可以进行UI操作，如Toast，Dialog等
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                tvTime.setText(simpleDateFormat.format(date));
            }
        }
    };

    public static void startScaleInAnimD(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.inanimation);
        if (view != null)
            view.startAnimation(animation);
    }

    public static void startScaleOutAnimD(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.ouanimation);
        if (view != null)
            view.startAnimation(animation);
    }

    private static void startTextAnimate(TextView view) {
        if (null != animatorSet) {
            animatorSet.cancel();
        }
        view.setText("Merits+1");
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        Animator transAnimator = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -70.0f);
        animatorSet.playTogether(alphaAnimator, transAnimator);
        animatorSet.start();
    }

    @Subscribe
    public void onBus(Bus bus) {
        if (bus != null) {
            if (Objects.equals(bus.type, SpConstants.BUS_CONSTANTS_COLOR)) {
                int color = (int) bus.data;
                d_onclick.setColorFilter(ColorUtils.getColor(color));
            } else if (Objects.equals(bus.type, SpConstants.BUS_CONSTANTS_REFRESH_NUM)) {
                tab = 0;
                SPUtils.getInstance().put(SpConstants.TOTAL_NUM, tab);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
