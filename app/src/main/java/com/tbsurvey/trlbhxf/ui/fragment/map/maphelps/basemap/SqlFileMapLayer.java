package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.data.TileKey;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.layers.ImageTiledLayer;
import com.orhanobut.logger.Logger;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.data.local.DBManager;
import com.tbsurvey.trlbhxf.utils.ImageDispose;

import static com.tbsurvey.trlbhxf.utils.butterknife.AppContext.getResources;


/**
 * author:jxj on 2020/11/11 09:19
 * e-mail:592296083@qq.com
 * desc  :
 */
public class SqlFileMapLayer extends ImageTiledLayer {
    private String dbPath;
    private SQLiteDatabase mDB;
    public SqlFileMapLayer(TileInfo tileInfo, Envelope fullExtent, String dbPath) {
        super(tileInfo, fullExtent);
        this.dbPath = dbPath;
        this.init();
    }
    private void init() {
        mDB = DBManager.getInstance().openDB(dbPath);
    }


    @Override
    protected byte[] getTile(TileKey tileKey) {
        Logger.d("Level:"+tileKey.getLevel()+"Column:"+tileKey.getColumn()+"Row:"+tileKey.getRow());
        byte[] bytes = null;
        if (mDB != null) {
            Cursor cursor = mDB.rawQuery("SELECT B.tile_data FROM map as A,images as B WHERE A.tile_id=B.tile_id  AND A.zoom_level = ? AND A.tile_column = ? AND A.tile_row = ?",
                    new String[]{String.valueOf(tileKey.getLevel()), String.valueOf(tileKey.getColumn()), String.valueOf(tileKey.getRow())});
            while (cursor.moveToNext()) {
                bytes = cursor.getBlob(0);
            }
            cursor.close();
            if (bytes==null||bytes.length==0){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nodata);
                bytes=  ImageDispose.Bitmap2Bytes(bitmap);
            }
//            mDB.close();
        }
        return bytes;
    }
}
