package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap;

import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.data.TileKey;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.internal.jni.CoreWebTiledLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * author:jxj on 2021/8/30 10:03
 * e-mail:592296083@qq.com
 * desc  :
 */
public class CacheTiledLayer extends WebTiledLayer {
    private String cachePath;
    public CacheTiledLayer(String templateUri) {
        super(templateUri);
    }
    public CacheTiledLayer(String templateUri, Iterable<String> subDomains, TileInfo tileInfo, Envelope fullExtent, String cachePath) {
        super(templateUri, subDomains, tileInfo, fullExtent);
        this.cachePath = cachePath;
    }


    @Override
    protected byte[] getTile(TileKey tileKey) {
        Logger.d(tileKey);
        byte[] bytes = null;
        if (cachePath != null)
            bytes = getOfflineCacheFile(cachePath, tileKey.getLevel(), tileKey.getColumn(), tileKey.getRow());
        if (bytes == null) {
            bytes = super.getTile(tileKey);
            if (cachePath != null)
                AddOfflineCacheFile(cachePath, tileKey.getLevel(), tileKey.getColumn(), tileKey.getRow(), bytes);
        }
        return bytes;
    }

    // 将图片保存到本地 目录结构可以随便定义 只要你找得到对应的图片
    private byte[] AddOfflineCacheFile(String cachePath, int level, int col, int row, byte[] bytes) {

        File file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File levelfile = new File(cachePath + "/" + level);
        if (!levelfile.exists()) {
            levelfile.mkdirs();
        }
        File rowfile = new File(cachePath + "/" + level + "/" + col + "x" + row
                + ".cmap");
        if (!rowfile.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(rowfile);
                out.write(bytes);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bytes;

    }

    // 从本地获取图片
    private byte[] getOfflineCacheFile(String cachePath, int level, int col, int row) {
        byte[] bytes = null;
        File rowfile = new File(cachePath + "/" + level + "/" + col + "x" + row
                + ".cmap");
        if (rowfile.exists()) {
            try {
                bytes = CopySdcardbytes(rowfile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            bytes = null;
        }
        return bytes;
    }

    // 读取本地图片流
    private byte[] CopySdcardbytes(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] bytes = out.toByteArray();
        return bytes;
    }
}
