package com.tbsurvey.trlbhxf.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.app.MyApplication;
import com.tbsurvey.trlbhxf.data.entity.LocalShp;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.tbsurvey.trlbhxf.utils.StringUtils.getString;

/**
 * author:jxj on 2020/10/16 16:22
 * e-mail:592296083@qq.com
 * desc  :
 */
public class FileUtils {
    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取SD卡绝对路径
     *
     * @return
     */
    public static String getSDCardPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 判断是否为文件夹 通过路径
     *
     * @param dirPath
     * @return
     */
    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否为文件夹
     *
     * @param file
     * @return
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 判断是否为文件 通过路径
     *
     * @param filePath
     * @return
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否为文件
     *
     * @param file
     * @return
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * 创建文件夹 通过路径
     *
     * @param dirPath
     * @return
     */
    public static boolean createDir(final String dirPath) {
        return createDir(getFileByPath(dirPath));
    }

    /**
     * 创建文件夹
     *
     * @param file
     * @return
     */
    public static boolean createDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 创建文件 通过路径
     *
     * @param filePath
     * @return
     */
    public static boolean createFile(final String filePath, final boolean deleteOldFile) {
        return createFile(getFileByPath(filePath), deleteOldFile);
    }

    /**
     * oldPath 和 newPath必须是新旧文件的绝对路径
     */
    public static File renameFile(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath)) {
            return null;
        }

        if (TextUtils.isEmpty(newPath)) {
            return null;
        }
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        boolean b = oldFile.renameTo(newFile);
        File file2 = new File(newPath);
        return file2;
    }

    /**
     * 2 * 通过文件路径直接修改文件名
     * 3 *
     * 4 * @param filePath 需要修改的文件的完整路径
     * 5 * @param newFileName 需要修改的文件的名称
     * 6 * @return
     * 7
     */
    public static boolean FixFileName(String filePath, String newFileName) {
        File f = new File(filePath);
        if (!f.exists()) { // 判断原文件是否存在（防止文件名冲突）
            return false;
        }
        newFileName = newFileName.trim();
        if ("".equals(newFileName) || newFileName == null) // 文件名不能为空
            return false;

//        if (f.isDirectory()) { // 判断是否为文件夹
//            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName;
//        } else {
//            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName
//                    + filePath.substring(filePath.lastIndexOf("."));
//        }
        File nf = new File(newFileName);
        try {
            f.renameTo(nf); // 修改文件名
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建文件
     *
     * @param file
     * @return
     */
    public static boolean createFile(final File file, final boolean deleteOldFile) {
        if (file == null) return false;
        if (!deleteOldFile) {
            if (file.exists()) return file.isFile();
        } else {
            if (file.exists() && !file.delete()) return false;
        }
        if (!createDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件 通过路径
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(final String filePath) {

        return deleteFile(getFileByPath(filePath));
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(final File file) {
        if (file == null) return false;
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        return deleteSingleFile(file);
    }

    /**
     * 删除文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }


    /**
     * 删除单文件
     *
     * @param file
     * @return
     */
    public static boolean deleteSingleFile(final File file) {
        file.setWritable(true, false);
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }
    public static void deletePic( Context context, String path){
        if(!TextUtils.isEmpty(path)){
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = context.getContentResolver();//cutPic.this是一个上下文
            String url =  MediaStore.Images.Media.DATA + "='" + path + "'";
            //删除图片
            contentResolver.delete(uri, url, null);
        }
    }
    public static void deletePictures(Context context,String file){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file1=new File(file);
        if (!file1.exists()){
            return;
        }
        Uri uri = Uri.fromFile(file1);
        intent.setData(uri);
        context.sendBroadcast(intent);
        file1.delete();
    }

    /**
     * 拷贝文件夹，通过路径
     *
     * @param srcDirPath
     * @param destDirPath
     * @return
     */
    public static boolean copyDir(final String srcDirPath,
                                  final String destDirPath) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 拷贝文件夹
     *
     * @param srcDir
     * @param destDir
     * @return
     */
    public static boolean copyDir(final File srcDir,
                                  final File destDir) {
        return copyOrMoveDir(srcDir, destDir, false);
    }

    /**
     * 移动文件夹  通过路径
     *
     * @param srcDirPath
     * @param destDirPath
     * @return
     */
    public static boolean moveDir(final String srcDirPath,
                                  final String destDirPath) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 移动文件夹
     *
     * @param srcDir
     * @param destDir
     * @return
     */
    public static boolean moveDir(final File srcDir,
                                  final File destDir) {
        return copyOrMoveDir(srcDir, destDir, true);
    }

    /**
     * 拷贝或移动文件夹  删除旧目标文件夹
     *
     * @param srcDir
     * @param destDir
     * @param isMove
     * @return
     */
    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final boolean isMove) {
        return copyOrMoveDir(srcDir, destDir, true, isMove);
    }

    /**
     * 拷贝或移动文件夹
     *
     * @param srcDir
     * @param destDir
     * @param delOldDestDir 删除旧目标文件夹
     * @param isMove        是否为移动
     * @return
     */
    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final boolean delOldDestDir,
                                         final boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        // destDir's path locate in srcDir's path then return false
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        if (destDir.exists()) {
            if (delOldDestDir) {// 删除旧文件夹
                if (!deleteAllInDir(destDir)) {// 删除失败
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyOrMoveFile(file, oneDestFile, delOldDestDir, isMove)) return false;
            } else if (file.isDirectory()) {
                if (!copyOrMoveDir(file, oneDestFile, delOldDestDir, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 拷贝文件 ，通过路径
     *
     * @param srcFilePath
     * @param destFilePath
     * @return
     */
    public static boolean copyFile(final String srcFilePath,
                                   final String destFilePath) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 拷贝文件
     *
     * @param srcFile
     * @param destFile
     * @return
     */
    public static boolean copyFile(final File srcFile,
                                   final File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 移动文件
     *
     * @param srcFilePath
     * @param destFilePath
     * @return
     */
    public static boolean moveFile(final String srcFilePath,
                                   final String destFilePath) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 移动文件
     *
     * @param srcFile
     * @param destFile
     * @return
     */
    public static boolean moveFile(final File srcFile,
                                   final File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final boolean isMove) {
        return copyOrMoveFile(srcFile, destFile, true, isMove);
    }

    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final boolean delOldDestFile,
                                          final boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        // srcFile equals destFile then return false
        if (srcFile.equals(destFile)) return false;
        // srcFile doesn't exist or isn't a file then return false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) {
            if (delOldDestFile) {// 删除旧文件
                if (!destFile.delete()) {// 删除失败
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createDir(destFile.getParentFile())) return false;
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile))
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除所有文件 ，通过路径
     *
     * @param dirPath
     * @return
     */
    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * 删除所有文件
     *
     * @param dir
     * @return
     */
    public static boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    /**
     * 删除文件，通过文件过滤
     *
     * @param dir
     * @param filter
     * @return
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }


    private static boolean writeFileFromIS(final File file,
                                           final FileInputStream is) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String lastName(File file) {
        if (file == null) return null;
        String filename = file.getName();
        // split用的是正则，所以需要用 //. 来做分隔符
        String[] split = filename.split("\\.");
        //注意判断截取后的数组长度，数组最后一个元素是后缀名
        if (split.length > 1) {
            return "."+split[split.length - 1];
        } else {
            return "";
        }
    }

    /**
     * 获取文件 通过路径
     *
     * @param filePath
     * @return
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getFilesAllName(String path) {
        //传入指定文件夹的路径
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> shpgePaths = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (checkIsShpFile(files[i].getPath())) {
                shpgePaths.add(files[i].getPath());
            }

        }
        return shpgePaths;
    }

    public static List<String> getFilesAllName5(String path) {
        List<String> shpgePaths = new ArrayList<>();
        //传入指定文件夹的路径
        File file = new File(path);
        if (!file.exists()){
            return shpgePaths;
        }
        File[] files = file.listFiles();
        if (files==null){
            return shpgePaths;
        }

        for (int i = 0; i < files.length; i++) {
            shpgePaths.add(files[i].getPath());
        }
        return shpgePaths;
    }

    public static List<LocalShp> getShpFilesAllName(String path) {
        //传入指定文件夹的路径
        File file = new File(path);
        File[] files = file.listFiles();
        List<LocalShp> shpgePaths = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            for (int j = 0; j < files[i].listFiles().length; j++) {
                if (checkIsShpFile(files[i].listFiles()[j].getPath())) {
                    LocalShp localShp = new LocalShp();
                    localShp.setName(files[i].getName());
                    localShp.setUri(files[i].listFiles()[j].getPath());
                    shpgePaths.add(localShp);
                }
            }


        }
        return shpgePaths;
    }

    public static List<String> getFilesAllName(String path, boolean ischeck) {
        //传入指定文件夹的路径
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> shpgePaths = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (checkIsShpFile(files[i].getPath()) || ischeck) {
                shpgePaths.add(files[i].getName());
            }

        }
        return shpgePaths;
    }

    public static List<String> getFilesImgAllName(String path) {
        //传入指定文件夹的路径
//        createDir(path);
        List<String> shpgePaths = new ArrayList<>();

        File file = new File(path);
        if (!file.exists()) {
            return shpgePaths;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return shpgePaths;
        }
        for (int i = 0; i < files.length; i++) {
            if (checkIsImgFile(files[i].getPath())) {
                shpgePaths.add(files[i].getName());
            }

        }
        return shpgePaths;
    }

    public static List<String> getFilesAzdbAllName(String path) {
        //传入指定文件夹的路径
//        createDir(path);
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> shpgePaths = new ArrayList<>();
        if (files == null) {
            return null;
        }
        for (int i = 0; i < files.length; i++) {
            if (checkIsAzdbFile(files[i].getPath())) {
                shpgePaths.add(files[i].getName());
            }

        }
        return shpgePaths;
    }

    public static List<String> getFilesTPKAllName(String path) {
        //传入指定文件夹的路径
//        createDir(path);
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> shpgePaths = new ArrayList<>();
        if (files == null) {
            return null;
        }
        for (int i = 0; i < files.length; i++) {
            if (checkIsTPKFile(files[i].getPath())) {
                shpgePaths.add(files[i].getName());
            }

        }
        return shpgePaths;
    }

    /**
     * 判断是否是shp
     */
    public static boolean checkIsShpFile(String fName) {
        boolean isImageFile = false;
        //获取拓展名
        String fileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (fileEnd.equals("shp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * 判断是否是tpk
     */
    public static boolean checkIsTPKFile(String fName) {
        boolean isImageFile = false;
        //获取拓展名
        String fileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (fileEnd.equals("tpk")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * 判断是否是图片
     */
    public static boolean checkIsImgFile(String fName) {
        boolean isImageFile = false;
        //获取拓展名
        String fileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (fileEnd.equals("jpg") || fileEnd.equals("png")|| fileEnd.equals("jpeg")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * 判断是否是azdb
     */
    public static boolean checkIsAzdbFile(String fName) {
        boolean isImageFile = false;
        //获取拓展名
        String fileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (fileEnd.equals("azdb")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    public static String getFileNameNoEx(String filename) {

        if ((filename != null) && (filename.length() > 0)) {

            int dot = filename.lastIndexOf('.');

            if ((dot > -1) && (dot < (filename.length()))) {

                return filename.substring(0, dot);

            }

        }

        return filename;

    }

    public static void writeFile(String fileName) {
        EncrypDES encrypDES = null;
        try {
            encrypDES = new EncrypDES();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File f = new File(fileName);
        if (f.exists()) {
            return;
        }
        createFile(fileName, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Files.write(Paths.get(fileName), encrypDES.encrypt(DateUtils.getSystemTime()).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //        //追加写模式
//        Files.write(Paths.get(filename),"追加写".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } else {
            try {

                PrintWriter printWriter = new PrintWriter(fileName, "utf-8");
                //一行一行写
                Logger.d(encrypDES.encrypt(DateUtils.getSystemTime()));
                printWriter.println(encrypDES.encrypt(DateUtils.getSystemTime()));
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static String ReadFile(String strFilePath) {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        content += line;
                    }
                    instream.close();
                }
            } catch (FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    public static boolean InitDb(String userName) {
        File userFile = new File(getSDCardPath() + "/" + getString(R.string.app_name) + "/数据库文件/" + userName + "/bzd.gpkg");
        if (userFile.exists()) {
            return false;
        }
        copyFile("bzd.gpkg", userName, false);
        return true;
    }

    public static void copyFile(String filename, String userName, boolean delete) {
        InputStream in = null;
        FileOutputStream out = null;
        // path为指定目录
        String path = getSDCardPath() + "/" + getString(R.string.app_name) + "/数据库文件/" + userName + "/" + filename;// data/data目录
        if (delete) {
            deleteFile(path);
        }
        File file1 = new File(getSDCardPath() + "/" + getString(R.string.app_name) + "/数据库文件/" + userName);
        if (!file1.exists()) {
            createDir(file1);
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                in = MyApplication.getMyApplication().getApplicationContext().getAssets().open("bzd.gpkg"); // 从assets目录下复制
                out = new FileOutputStream(file);
                int length = -1;
                byte[] buf = new byte[1024];
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
