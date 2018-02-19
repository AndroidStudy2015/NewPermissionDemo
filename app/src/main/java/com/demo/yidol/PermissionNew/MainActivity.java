package com.demo.yidol.PermissionNew;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private Button store;
    private Button read;
    private String tag = "MainActivity";
    private String[] mPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        store = findViewById(R.id.bt_store);
        read = findViewById(R.id.bt_read);

        mPermissions = new String[]{
//                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        onClickStore();
        onClickRead();
    }

    private void onClickStore() {
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rationale = "sdfsfs";
                PermissionUtils.needPermission(MainActivity.this, mPermissions[0], rationale, new PermissionUtils.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e(tag, "获取权限");
                        StoreUtils.storeStringToSDCard(MainActivity.this, "MainActivity", "qwe.txt", "aaa");
                    }
                    @Override
                    public void onFaliue() {
                        Log.e(tag, "权限拒绝了");
                    }
                });

            }

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {


        Log.e("qqq","======sdfdsfdsfdsffsfs");

        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以去放肆了。
                    StoreUtils.storeStringToSDCard(MainActivity.this, "MainActivity", "qwe.txt", "aaa");


                } else {
                    // 权限被用户拒绝了，洗洗睡吧。

                }
                return;
            }
        }
    }


    private void onClickRead() {
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               File file = new File(Environment.getExternalStorageDirectory() + "/" + "aaa", "qwe.txt");

                Toast.makeText(MainActivity.this,StoreUtils.readStringFromSDCard(file),Toast.LENGTH_LONG).show();
            }
        });
    }

}
