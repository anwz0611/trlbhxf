//package com.tbsurvey.trlbhxf.data.local;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//import com.tbsurvey.trlbhxf.data.http.entity.BaseHttpResult;
//
///**
// * author:jxj on 2021/8/24 15:46
// * e-mail:592296083@qq.com
// * desc  :
// */
//@Database(entities = {}, version = 1)
//public abstract class AppDatabase extends RoomDatabase {
////    public abstract UserDao userDao();
//    private static volatile AppDatabase INSTANCE;
//    public static AppDatabase getDatabase(Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, "trlbhxf").build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}
//
