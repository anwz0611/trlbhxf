package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw;

import android.graphics.Color;

import com.esri.arcgisruntime.symbology.FillSymbol;
import com.esri.arcgisruntime.symbology.LineSymbol;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.tbsurvey.trlbhxf.R;

import static com.tbsurvey.trlbhxf.utils.StringUtils.getColor;

/**
 * author:jxj on 2020/8/18 10:47
 * e-mail:592296083@qq.com
 * desc  :要素编辑状态符号化信息
 */
public class DrawSymbol {

    private static int SIZE = 12;//节点大小

    public static MarkerSymbol markerSymbol  = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE,Color.RED, SIZE );
    public static SimpleMarkerSymbol mRedMarkerSymbol = new SimpleMarkerSymbol( SimpleMarkerSymbol.Style.SQUARE,Color.RED, SIZE);
    public static SimpleMarkerSymbol mBlackMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE,Color.BLACK, SIZE);
    public static SimpleMarkerSymbol mGreenMarkerSymbol = new SimpleMarkerSymbol( SimpleMarkerSymbol.Style.SQUARE,Color.GREEN, SIZE);
    public static SimpleMarkerSymbol mCyanMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE,Color.CYAN, 15);
    public static LineSymbol mLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,Color.RED, 2);
    public static LineSymbol mLineSymbol1 = new SimpleLineSymbol( SimpleLineSymbol.Style.DASH,Color.RED, 2);
    public static FillSymbol mFillSymbol =  new SimpleFillSymbol(SimpleFillSymbol.Style.NULL,Color.YELLOW,mLineSymbol);
    public static FillSymbol mFillSymbol11 =  new SimpleFillSymbol(SimpleFillSymbol.Style.NULL,Color.RED,mLineSymbol);

    public static LineSymbol mSelectLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.YELLOW, 1f);
    public static LineSymbol mSelectLineSymbol1 = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, getColor(R.color.md_red_200), 1f);
    public static FillSymbol mFillSymbol1 = new SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.YELLOW, mSelectLineSymbol);
    public static FillSymbol mYztFill = new SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.BLACK, mSelectLineSymbol1);
    public static SimpleMarkerSymbol otherRedMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, Color.RED, 20);
    public static SimpleMarkerSymbol mBLUEMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.TRIANGLE, Color.BLUE, 20);
    public static SimpleMarkerSymbol mYellowMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.TRIANGLE, Color.YELLOW, 20);
    public static SimpleMarkerSymbol jgdCyanMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.CYAN, 10);


}
