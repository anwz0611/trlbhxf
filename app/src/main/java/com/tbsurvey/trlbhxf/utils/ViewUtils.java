package com.tbsurvey.trlbhxf.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.annotation.Nullable;

/**
 * author:jxj on 2021/8/25 15:53
 * e-mail:592296083@qq.com
 * desc  :
 */
public class ViewUtils {
    /**
     * <p>对 View 做上下位移的进场动画</p>
     * <p>相关方法 {@link #slideOut(View, int, Animation.AnimationListener, boolean, Direction)}</p>
     *
     * @param view      做动画的 View
     * @param duration  动画时长(毫秒)
     * @param listener  动画回调
     * @param direction 进场动画的方向
     * @return 动画对应的 Animator 对象, 注意无动画时返回 null
     */
    @Nullable
    public static TranslateAnimation slideIn(final View view, int duration, final Animation.AnimationListener listener, Direction direction) {
        return slideIn(view, duration, listener, true, direction);
    }

    /**
     * <p>对 View 做上下位移的进场动画</p>
     * <p>相关方法 {@link #slideOut(View, int, Animation.AnimationListener, boolean, Direction)}</p>
     *
     * @param view            做动画的 View
     * @param duration        动画时长(毫秒)
     * @param listener        动画回调
     * @param isNeedAnimation 是否需要动画
     * @param direction       进场动画的方向
     * @return 动画对应的 Animator 对象, 注意无动画时返回 null
     */
    @Nullable
    public static TranslateAnimation slideIn(final View view, int duration, final Animation.AnimationListener listener, boolean isNeedAnimation, Direction direction) {
        if (view == null) {
            return null;
        }
        if (isNeedAnimation) {
            TranslateAnimation translate = null;
            switch (direction) {
                case LEFT_TO_RIGHT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case TOP_TO_BOTTOM:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case RIGHT_TO_LEFT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case BOTTOM_TO_TOP:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                default:
                    break;
            }
            translate.setInterpolator(new DecelerateInterpolator());
            translate.setDuration(duration);
            translate.setFillAfter(true);
            if (listener != null) {
                translate.setAnimationListener(listener);
            }
            view.setVisibility(View.VISIBLE);
            view.startAnimation(translate);
            return translate;
        } else {
            view.clearAnimation();
            view.setVisibility(View.VISIBLE);
            return null;
        }
    }

    /**
     * <p>对 View 做上下位移的退场动画</p>
     * <p>相关方法 {@link #slideIn(View, int, Animation.AnimationListener, boolean, Direction)}</p>
     *
     * @param view      做动画的 View
     * @param duration  动画时长(毫秒)
     * @param listener  动画回调
     * @param direction 进场动画的方向
     * @return 动画对应的 Animator 对象, 注意无动画时返回 null
     */
    @Nullable
    public static TranslateAnimation slideOut(final View view, int duration, final Animation.AnimationListener listener, Direction direction) {
        return slideOut(view, duration, listener, true, direction);
    }

    /**
     * <p>对 View 做上下位移的退场动画</p>
     * <p>相关方法 {@link #slideIn(View, int, Animation.AnimationListener, boolean, Direction)}</p>
     *
     * @param view            做动画的 View
     * @param duration        动画时长(毫秒)
     * @param listener        动画回调
     * @param isNeedAnimation 是否需要动画
     * @param direction       进场动画的方向
     * @return 动画对应的 Animator 对象, 注意无动画时返回 null
     */
    @Nullable
    public static TranslateAnimation slideOut(final View view, int duration, final Animation.AnimationListener listener, boolean isNeedAnimation, Direction direction) {
        if (view == null) {
            return null;
        }
        if (isNeedAnimation) {
            TranslateAnimation translate = null;
            switch (direction) {
                case LEFT_TO_RIGHT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case TOP_TO_BOTTOM:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
                    );
                    break;
                case RIGHT_TO_LEFT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case BOTTOM_TO_TOP:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f
                    );
                    break;
                default:
                    break;
            }
            translate.setInterpolator(new DecelerateInterpolator());
            translate.setDuration(duration);
            translate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                    if (listener != null) {
                        listener.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationRepeat(animation);
                    }
                }
            });
            view.startAnimation(translate);
            return translate;
        } else {
            view.clearAnimation();
            view.setVisibility(View.GONE);
            return null;
        }
    }
    /**
     * 方向
     */
    public enum Direction {
        /**
         * 左 -> 右
         */
        LEFT_TO_RIGHT,
        /**
         * 上 -> 下
         */
        TOP_TO_BOTTOM,
        /**
         * 右 -> 左
         */
        RIGHT_TO_LEFT,
        /**
         * 下 -> 上
         */
        BOTTOM_TO_TOP
    }
}
