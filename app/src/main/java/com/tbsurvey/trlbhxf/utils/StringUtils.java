package com.tbsurvey.trlbhxf.utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.StringRes;


import com.tbsurvey.trlbhxf.data.entity.VuforiaImageTarget;
import com.tbsurvey.trlbhxf.utils.butterknife.AppContext;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:jxj on 2020/9/11 14:07
 * e-mail:592296083@qq.com
 * desc  :
 */
public class StringUtils {
    public static DecimalFormat df = new DecimalFormat("0.000000");
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Date toDate(String time) {
        Date dateTime = new Date();
        try {
            dateTime = formatter.parse(time);
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;

    }

    public static String getDf(Object ss) {
        return df.format(ss);
    }

    public static double setScale(double ss) {
        BigDecimal bd = new BigDecimal(ss);
        return bd.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static double setScale2(double ss) {
        BigDecimal bd = new BigDecimal(ss);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 找出两个list中的不同元素
     * 用Map存放List1和List2的元素作为key，value为其在List1和List2中出现的次数
     * 出现次数为1的即为不同元素，查找次数为list1.size() + list2.size()，较方法1和2，是极大简化
     * 【效率最高】
     *
     * @param listA
     * @param listB
     * @return
     */
    public static List<String> getDiff(List<String> listA, List<String> listB) {
        List<String> diff = new ArrayList<String>();
        List<String> maxList = listA;
        List<String> minList = listB;
        if (listB.size() > listA.size()) {
            maxList = listB;
            minList = listA;
        }
        Map<String, Integer> map = new HashMap<String, Integer>(maxList.size());
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            if (map.get(string) != null) {
                map.put(string, 2);
                continue;
            }
            diff.add(string);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    public static String base64(String passWord) {
        Base64.Encoder encoder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                encoder = Base64.getEncoder();
                byte[] textByte = passWord.getBytes("UTF-8");
                String encodedText = encoder.encodeToString(textByte);
                return encodedText;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            return Base64Utils.getInstance().encode(passWord);
        }
        return "";
    }

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符 false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String getDYDBH(int a) {
        if (a > 9) {
            return a + "";
        } else {
            return "0" + a;
        }

    }

    public static String getYDBH(int a) {
        if (a <= 9) {
            return "0000" + a;
        } else if (a <= 99) {
            return "000" + a;
        } else if (a <= 999) {
            return "00" + a;
        } else if (a <= 9999) {
            return "0" + a;
        }
        return "MAX";
    }

    public static String getDYABH(int a) {
        if (a <= 9) {
            return "000" + a;
        } else if (a <= 99) {
            return "00" + a;
        } else if (a <= 999) {
            return "0" + a;
        } else if (a <= 9999) {
            return "" + a;
        }
        return "MAX";
    }

    public static String setGMBH(int a) {
        if (a <= 9) {
            return "0" + a;
        } else if (a <= 99) {
            return "" + a;
        }
        return a+"";
    }
    public static int getGMBH(String a) {
        switch (a.length()) {
            case 2:
                if (a.startsWith("0")) {
                    Integer.parseInt(a.substring(1));
                } else {
                    return Integer.parseInt(a);
                }
                break;
        }
        return Integer.parseInt(a);
    }
    public static View removeFromParent(View v) {
        if (v == null) return null;
        ViewParent p = v.getParent();
        if (p instanceof ViewGroup) {
            ((ViewGroup) p).removeViewInLayout(v);
        }
        return v;
    }

    public static boolean indexInList(List<?> target, int index) {
        if (target == null) return false;
        return index >= 0 && index < target.size();
    }

    public static boolean isEmpty(Collection<?> datas) {
        return datas == null || datas.size() <= 0;
    }

    public static boolean noEmpty(CharSequence source) {
        return !TextUtils.isEmpty(source);
    }

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static String getString(String str) {
        return str == null ? "" : str;
    }

    /**
     * 从资源文件拿到文字
     */
    public static String getString(@StringRes int strId, Object... objs) {
        if (strId == 0) return null;
        return AppContext.getAppContext().getResources().getString(strId, objs);
    }

    /**
     * 从资源文件拿到颜色
     */
    public static int getColor(int colorId) {
        if (colorId == 0) return 0;
        return AppContext.getAppContext().getResources().getColor(colorId);
    }

    /**
     * 从资源文件拿到图片
     */
    public static Drawable getDrawable(int strId) {
        if (strId == 0) return null;
        return AppContext.getAppContext().getResources().getDrawable(strId);
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 16进制字符串 转换为对应的 byte数组
     */
    public static byte[] hex2Bytes(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        char[] hexChars = hex.toCharArray();
        byte[] bytes = new byte[hexChars.length / 2];   // 如果 hex 中的字符不是偶数个, 则忽略最后一个

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt("" + hexChars[i * 2] + hexChars[i * 2 + 1], 16);
        }

        return bytes;
    }

    public static String Hex2Str(byte[] hexByteIn) {
        int len = hexByteIn.length;
        String restult = "";
        for (byte b : hexByteIn) {
            restult += String.format("%02x", b);
        }
        return restult;
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static byte[] hexStr2Byte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

    public static String str2HexStr(String origin, String charsetName) {
        byte[] bytes = new byte[0];
        try {
            bytes = origin.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String hex = bytesToHexString(bytes);
        return hex;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String hexStr2Str(String hex) {
        byte[] bb = hexStringToBytes(hex);
        String rr = new String(bb);
        return rr;
    }

    /**
     * byteBuffer 转 byte数组
     *
     * @param buffer
     * @return
     */
    public static byte[] bytebuffer2ByteArray(ByteBuffer buffer) {
        //重置 limit 和postion 值
        buffer.flip();
        //获取buffer中有效大小
        int len = buffer.limit() - buffer.position();

        byte[] bytes = new byte[len];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get();

        }

        return bytes;
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 判断对象为空
     *
     * @param obj 对象名
     * @return 是否为空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof List)) {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String)) {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        return df.format(new Date());
    }

    public static String getNowTime1() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式

        return df.format(new Date());
    }

    //读取XML
    public static List<VuforiaImageTarget> readXML(InputStream inStream) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inStream, "UTF-8");
            int eventType = parser.getEventType();
            VuforiaImageTarget currentPerson = null;
            List<VuforiaImageTarget> persons = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://文档开始事件,可以进行数据初始化处理
                        persons = new ArrayList<VuforiaImageTarget>();
                        break;
                    case XmlPullParser.START_TAG://开始元素事件
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("Field")) {
                            currentPerson = new VuforiaImageTarget();
                            currentPerson.name = parser.getAttributeValue(null, "name");// 如果后面是Text元素,即返回它的值
                            currentPerson.value = parser.getAttributeValue(null, "value");
                        }
                        break;
                    case XmlPullParser.END_TAG://结束元素事件

                        if (parser.getName().equalsIgnoreCase("Field") && currentPerson != null) {
                            persons.add(currentPerson);
                            currentPerson = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            inStream.close();
            return persons;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> Iterable<T> iterableReverseList(final List<T> l) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    ListIterator<T> listIter = l.listIterator(l.size());

                    public boolean hasNext() {
                        return listIter.hasPrevious();
                    }

                    public T next() {
                        return listIter.previous();
                    }

                    public void remove() {
                        listIter.remove();
                    }
                };
            }
        };
    }

    /**
     * 经纬度校验
     * 经度longitude: (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6})
     * 纬度latitude：  (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     *
     * @return
     */
    public static boolean checkItude(String longitude, String latitude) {
        String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        longitude = longitude.trim();
        latitude = latitude.trim();
        return longitude.matches(reglo) == true ? latitude.matches(regla) : false;
    }

    public static boolean testGPS(String longitude, String latitude) {
        //经度： -180.0～+180.0（整数部分为0～180，必须输入1到8位小数）
        String longitudePattern = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,8}|1[0-7]?\\d{1}\\.\\d{1,8}|180\\.0{1,8})$";
        //纬度： -90.0～+90.0（整数部分为0～90，必须输入1到8位小数）
        String latitudePattern = "^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,8}|90\\.0{1,8})$";
        boolean longitudeMatch = Pattern.matches(longitudePattern, longitude);
        boolean latitudeMatch = Pattern.matches(latitudePattern, latitude);
        if (longitudeMatch && latitudeMatch) {
            return true;
        }
        return false;
    }
}
