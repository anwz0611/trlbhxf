package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap;

import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.data.TileKey;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.layers.ImageTiledLayer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author:jxj on 2021/6/24 10:12
 * e-mail:592296083@qq.com
 * desc  :
 */
public abstract class xImageTiledLayer extends ImageTiledLayer {

    public xImageTiledLayer(TileInfo tileInfo, Envelope fullExtent) {
        super(tileInfo, fullExtent);
    }

    @Override
    protected byte[] getTile(TileKey tileKey) {
        byte[] iResult = null;
        try {
            URL iURL = null;
            byte[] iBuffer = new byte[1024];
            HttpURLConnection iHttpURLConnection = null;
            BufferedInputStream iBufferedInputStream = null;
            ByteArrayOutputStream iByteArrayOutputStream = null;

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36 Edg/88.0.705.81";

            iURL = new URL(this.getTileUrl(tileKey));
            iHttpURLConnection = (HttpURLConnection) iURL.openConnection();
            iHttpURLConnection.setRequestProperty("User-Agent", userAgent);//为连接设置ua
            iHttpURLConnection.connect();
            iBufferedInputStream = new BufferedInputStream(iHttpURLConnection.getInputStream());
            iByteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int iLength = iBufferedInputStream.read(iBuffer);
                if (iLength > 0) {
                    iByteArrayOutputStream.write(iBuffer, 0, iLength);
                } else {
                    break;
                }
            }
            iBufferedInputStream.close();
            iHttpURLConnection.disconnect();

            iResult = iByteArrayOutputStream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return iResult;
    }

    protected abstract String getTileUrl(TileKey tileKey);
}
