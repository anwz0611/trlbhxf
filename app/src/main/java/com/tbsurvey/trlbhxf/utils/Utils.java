package com.tbsurvey.trlbhxf.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.DisplayMetrics;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.app.MyApplication;
import com.tbsurvey.trlbhxf.data.entity.BitmapBean;

import static com.tbsurvey.trlbhxf.utils.butterknife.AppContext.getResources;

/**
 * author:jxj on 2020/10/13 19:03
 * e-mail:592296083@qq.com
 * desc  :
 */
public final class Utils {

    //==========图片选择===========//

    /**
     * 获取图片选择的配置
     *
     * @param fragment
     * @return
     */
//    public static PictureSelectionModel getPictureSelector(Fragment fragment) {
//        return PictureSelector.create(fragment)
//                .openGallery(PictureMimeType.ofImage())
//                .theme(R.style.XUIPictureStyle)
//                .maxSelectNum(8)
//                .minSelectNum(1)
//                .selectionMode(PictureConfig.MULTIPLE)
//                .previewImage(true)
//                .isCamera(true)
//                .enableCrop(false)
//                .compress(true)
//                .previewEggs(true);
//    }
    //==========图片选择===========//

//    /**
//     * 获取图片选择的配置
//     *
//     * @param //fragment
//     * @return
//     */
//    public static PictureSelectionModel getPictureSelector(Activity activity) {
//        return PictureSelector.create(activity)
//                .openGallery(PictureMimeType.ofImage())
//                .theme(R.style.XUIPictureStyle)
//                .maxSelectNum(8)
//                .minSelectNum(1)
//                .selectionMode(PictureConfig.SINGLE)
//                .previewImage(true)
//                .isCamera(true)
//                .enableCrop(false)
//                .compress(true)
//                .previewEggs(true);
//    }
    /**
     * 文字转换BitMap
     * @param text
     * @return
     */
    public static BitmapBean generateBitmap(String text){
        TextPaint textPaint = new TextPaint();

        int textSizePx=20;
        int textColor=getResources().getColor(R.color.md_blue_500);
        textPaint.setTextSize(textSizePx);
        textPaint.setColor(textColor);
        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        int width = (int) Math.ceil(textPaint.measureText(text));
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int height = (int) Math.ceil(Math.abs(fontMetrics.bottom) + Math.abs(fontMetrics.top));
        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text,0,Math.abs(fontMetrics.ascent),textPaint);
        return new BitmapBean(height,width,new BitmapDrawable(bitmap));
    }
    /**
     * dip转px
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue){
        final float scale = getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    /**
     * px转dip
     * @param pxValue
     * @return
     */
    public static int px2dip( float pxValue){
        final float scale = getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * getDisplayMetrics
     * @param
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(){
        return MyApplication.getMyApplication().getResources().getDisplayMetrics();
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context){
        DisplayMetrics dm = getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return new Point(width, height);

    }

    /**
     * 获取屏幕长宽比
     * @param context
     * @return
     */
    public static float getScreenRate(Context context){
        Point p = getScreenMetrics(context);
        float h = p.y;
        float w = p.x;
        return (h/w);
    }

}
