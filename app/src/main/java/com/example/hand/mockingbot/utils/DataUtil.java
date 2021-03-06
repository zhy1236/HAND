package com.example.hand.mockingbot.utils;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Oliver on 2016/10/21.
 */

public class DataUtil {

    private static String url;

    public static Object listGetValueOf(List list, int index) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ex) {
        }
        return null;
    }

    public static boolean listHasValueOf(List list, int index) {
        try {
            return list.get(index) != null;
        } catch (IndexOutOfBoundsException ex) {

        }
        return false;
    }

    public static String parseDateNormalString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String parseDateByFormat(String dateStr, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            return "";
        }
        if (date != null) {
            return new SimpleDateFormat(format).format(date);
        } else {
            return "";
        }

    }




    /**
     * 转换字符串到正整型，转换失败或者为负数则返回-1
     *
     * @param value 待转换的字符串
     * @return >=0
     */
    public static int parseString2UnsignedInt(String value) {
        int parsed;
        try {
            parsed = Integer.valueOf(value);
            if (parsed >= 0) {
                return parsed;
            } else {
                return 0;
            }
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * 输入 是 否 <空> ，仅在为“是”时返回true
     *
     * @param value 传入的数值
     * @return
     */
    public static boolean parseString2Boolean(String value) {
        if (value.equals("是")) {
            return true;
        } else {
            return false;
        }
    }

    public static double parseString2DoubleFormat(String value) {
        try {
            String parsedString = String.format("%.2f", value);
            return Double.valueOf(parsedString);
        } catch (IllegalArgumentException ex) {
            return -1;
        }
    }



    public static String replaceBracketsOfString(String string) {
        if (string == null) {
            string = "";
        }
        int firstBracket = string.indexOf("(");
        if (firstBracket == -1) {
            firstBracket = string.indexOf("（");
        }
        if (firstBracket != -1) {
            return string.substring(0, firstBracket);
        }
        return "";
    }

    public static String getFileUrl(String fileUrl, String workFlowType) {
        Log.d("DataUtil", url);
        return url;
    }

    public static String valueNullKeepString(Object object) {
        if (object != null && object instanceof String) {
            return (String) object;
        }
        return "";
    }

    public static String emptyStringKeepZeroInteger(String value) {
        if (value != null && TextUtils.isEmpty(value)) {
            return "0";
        }
        return value;
    }
    public static void openFile(Context context, File file) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            String type = getMIMEType(file);
            intent.setDataAndType(Uri.fromFile(file), type);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openFile(Context context, Uri uri, String extension) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, getTypeFromExtension(extension));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
        }
    }


    public static String getTypeFromExtension(String extension){
        if(!extension.startsWith(".")){
            extension+=".";
        }
        return mapMIME.get(extension);
    }

    public static String getExtensionFromIME(String attachType){
        for (String key : mapMIME.keySet()) {
            String keyValue = mapMIME.get(key);
            if (keyValue.equals(attachType)) {
                return key;
            }
        }
        return null;
    }

    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");
        Log.e("sasd",file.getAbsolutePath());
        if (dotIndex < 0) {
            return type;
        }
        String end = fName.substring(dotIndex, fName.length()).toLowerCase(Locale.getDefault());
        if (end == "")
            return type;
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static String[][] MIME_MapTable = {
            { ".bmp", "image/bmp" },
            { ".doc", "application/msword" },
            { ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
            { ".xls", "application/vnd.ms-excel" },
            { ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            { ".gif", "image/gif" },
            { ".jpeg", "image/jpeg" },
            { ".jpg", "image/jpeg" },
            { ".png", "image/png" },
            { ".txt", "text/plain" },
            { ".wps", "application/vnd.ms-works" },
            { ".xml", "text/plain" },
    };

    public static File base64ToFile(String base64, String lastStr) {
        File file = null;
        String fileName = "Geely."+lastStr;
        FileOutputStream out = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public static File base64ToFileWithName(String base64, String lastStr, String name) {
        File file = null;
        String fileName =name+lastStr;
        FileOutputStream out = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    private static Map<String,String> mapMIME=new HashMap<>();

    static {
        mapMIME.put( ".bmp", "image/bmp" );
        mapMIME.put( ".doc", "application/msword" );
        mapMIME.put( ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" );
        mapMIME.put( ".xls", "application/vnd.ms-excel" );
        mapMIME.put( ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
        mapMIME.put( ".gif", "image/gif" );
        mapMIME.put( ".jpeg", "image/jpeg" );
        mapMIME.put( ".jpg", "image/jpeg" );
        mapMIME.put( ".png", "image/png" );
        mapMIME.put( ".txt", "text/plain" );
        mapMIME.put( ".wps", "application/vnd.ms-works" );
        mapMIME.put( ".xml", "text/plain" );

    }

    public static Map<String,String> getMapMIME(){
        return mapMIME;
    }

    public static String getStoragePath(Context mContext,boolean is_removal){
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removal == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
