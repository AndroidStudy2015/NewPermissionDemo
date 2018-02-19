package com.demo.yidol.PermissionNew;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by apple on 2017/12/23.
 */

public class PermissionUtils implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static String tag = "ccc";

    @NonNull
    public static void needPermission(final Activity activity, @Nullable String permission, String rationale, Callback callback) {  //

        mCallback = callback;
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限。


            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) activity, permission)) {
                //当拒绝了授权后，为提升用户体验，可以以弹窗的方式引导用户到设置中去进行设置
                new AlertDialog.Builder(activity)
                        .setMessage(rationale + "需要开启权限才能使用此功能")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //引导用户到设置中去进行设置
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                                activity.startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            } else {
                // 申请授权。
                Log.e(tag, "开始请求读写存储卡权限");
                Log.e(tag, "开始请求读写存储卡权限");
                Log.e(tag, "开始请求读写存储卡权限");
                Log.e(tag, "开始请求读写存储卡权限");
                Log.e(tag, "开始请求读写存储卡权限");

                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        100);

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           int[] grantResults) {


        Log.e("qqq","======sdfdsfdsfdsffsfs");

        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以去放肆了。
                    if (mCallback != null) {
                        mCallback.onSuccess();
                    }
                } else {
                    // 权限被用户拒绝了，洗洗睡吧。
                    if (mCallback != null) {
                        mCallback.onFaliue();
                    }
                }
                return;
            }
        }
    }

  private static Callback mCallback;

   public interface Callback {
        void onSuccess();

        void onFaliue();
    }
}
