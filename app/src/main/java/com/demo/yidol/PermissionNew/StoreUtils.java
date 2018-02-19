package com.demo.yidol.PermissionNew;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class StoreUtils {

    /**
     * SD卡存储注意事项：
     *1. 声明写sd卡的权限(清单文件)
     *2. 获得sd卡的目录：Environment.getExternalStorageDirectory()
     *3. 判断sd卡的状态.是否存在,是否可以读写:Environment.getExternalStorageState();
     *4. 判断sd卡剩余可用空间:Environment.getExternalStorageDirectory().getFreeSpace();
     */

    /**
     * 把String保存到sd文件中
     * 默认路径为：/mnt/sdcard/
     * 但是不同的手机是不一样的所以要使用：Environment.getExternalStorageDirectory()来获取系统的sdcard目录
     *
     * @param context             上下文
     * @param content             要存储在sd卡中的文本内容
     * @param fileName            存储的文件名
     * @param secondaryStorageDir 存储在sd卡中的二级目录的文件夹名称，如果为null，则存储在sd卡根目录下
     */
    public static void storeStringToSDCard(Context context, String content, String fileName, String secondaryStorageDir) {
        try {
//            获取sd卡的状态（是否挂载，是否可读写）
            String status = Environment.getExternalStorageState();
//            获取sd卡的可用空间
            long usableSpace = Environment.getExternalStorageDirectory().getUsableSpace();
            //判断sd卡的状态是否有,是否可以被读写.
            if (Environment.MEDIA_MOUNTED.equals(status)) {//sd卡已经挂载，可读可写

                if (usableSpace < 1024) {//判断SD剩余空间是否够用
                    Toast.makeText(context, "SD卡可用空间不足", Toast.LENGTH_SHORT).show();
                    return;
                }
                File file;
                if (secondaryStorageDir == null) {
                    file = new File(Environment.getExternalStorageDirectory(), fileName);

                } else {
                    file = new File(Environment.getExternalStorageDirectory() + "/" + secondaryStorageDir, fileName);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.close();
                Toast.makeText(context, "存储数据到sd卡成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "sd卡不可写,请检查sd卡状态", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "存储数据到sd卡失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 读取存储在SD卡中的文件
     * 默认路径为：/mnt/sdcard/
     * 但是不同的手机是不一样的所以要使用：Environment.getExternalStorageDirectory()来获取系统的sdcard目录
     *
     * @param file
     * @return 读取结果String
     */
    public static String readStringFromSDCard(File file) {
//            获取sd卡的状态（是否挂载，是否可读写）
        String status = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(status)) {//sd卡已经挂载，可读可写
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String content = "";
                String tmp;
                while ((tmp = br.readLine()) != null) {
                    content += tmp;
                }
                br.close();
                fis.close();
                return content;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "不存在此文件，请核对文件路径、文件名";
            } catch (Exception e) {
                e.printStackTrace();
                return "文件读取失败";
            }
        } else {
            return "SD卡不可用，请检查sd卡状态";
        }

    }
}