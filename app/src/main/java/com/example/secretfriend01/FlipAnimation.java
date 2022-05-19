package com.example.secretfriend01;

import android.content.Context;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FlipAnimation implements Animation.AnimationListener {
    private Animation animation1;
    private Animation animation2;
    private boolean isBackOfCardShowing = true;
    private TextView txt;
    private Context ctx;
    private FlipEnd flipped;
    private String s1,s2;

    public interface FlipEnd {
        void flipEnd(TextView txt);
    }

    FlipAnimation(Context ctx, TextView txt, boolean f,String s1, String s2) {
        this.txt = txt;
        this.ctx = ctx;
        isBackOfCardShowing = f;
        this.s1 = s1;
        this.s2 = s2;
        flipped = (FlipEnd) ctx;
        animation1 = AnimationUtils.loadAnimation(ctx, R.anim.flip_to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(ctx, R.anim.flip_from_middle);
        animation2.setAnimationListener(this);

        txt.clearAnimation();
        txt.setAnimation(animation1);
        txt.startAnimation(animation1);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animation1) {
            if (isBackOfCardShowing) {
                txt.setText(s1);
                flipped.flipEnd(txt);
            } else {
                txt.setText(s2);
            }
            txt.clearAnimation();
            txt.setAnimation(animation2);
            txt.startAnimation(animation2);
        } else {
            isBackOfCardShowing = !isBackOfCardShowing;
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
