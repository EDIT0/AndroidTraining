package com.example.logcollectordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnMakeFile;

    ZipManager zipManager = new ZipManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogCollector.makeLogDir(getApplicationContext(), LogCollector.DIR_INFOCONN);
        LogCollector.settingLogFile(LogCollector.LOG_FILE_HEADER_DIGITALKEY);
        LogCollector.d(true, true, "아아아 어어어");
        LogCollector.e(true, false, "ㅈㅂㅇㅂㅈㅇㅂㅈㅇㅂㅈㅇㅈㅂㅈㅂㄼㅈㄼㅈㅇㅂ ㅈㅇㅈㅂㅈㅇㅂㅈㅇㅂㅈㅇㅂㅈfewfwecwecewcwecewcewcewce\nwqwmkdqwkdnk\nㅇㅈㅂㅇㅂㅈㅇㅂ\ndqwqwqwdqwdqㅂㅇㅂㅈㅇㅈㅂㅇㅈㅂㅇㅂ\nwedqwwqdqq");
        LogCollector.i(false, true, "ㅈㅂㄼㅈㅈㅂㅇㅂㅈ");
        LogCollector.w(false, false, "dqw\nd\nq\nw\nd\nqwdavwevwefwqdhqwkdlkqwh k\ndwqdwd\n");
        LogCollector.v(true, true, "wqrqwtqw\ng\nqwrewfewfewfew\ngewwe\nfefewwefw\nwr32r33q");


//        LoggerCollector.makeLogDir(getApplicationContext(), LogCollector.INFOCONN);
//        LoggerCollector.settingLogFile(LogCollector.LOG_FILE_HEADER_DIGITALKEY);
//        LoggerCollector.d(true, true, "아아아 어어어");
//        LoggerCollector.e(true, false, "ㅈㅂㅇㅂㅈㅇㅂㅈㅇㅂㅈㅇㅈㅂㅈㅂㄼㅈㄼㅈㅇㅂ ㅈㅇㅈㅂㅈㅇㅂㅈㅇㅂㅈㅇㅂㅈfewfwecwecewcwecewcewcewce\nwqwmkdqwkdnk\nㅇㅈㅂㅇㅂㅈㅇㅂ\ndqwqwqwdqwdqㅂㅇㅂㅈㅇㅈㅂㅇㅈㅂㅇㅂ\nwedqwwqdqq");
//        LoggerCollector.i(false, true, "ㅈㅂㄼㅈㅈㅂㅇㅂㅈ");
//        LoggerCollector.d(this, true, true, "dqw\nd\nq\nw\nd\nqwdavwevwefwqdhqwkdlkqwh k\ndwqdwd\n");
//        LoggerCollector.v(true, true, "wqrqwtqwgqwrq");

        btnMakeFile = (Button) findViewById(R.id.btnMakeFile);
        btnMakeFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        String filePath = getExternalCacheDir() + "/log" + "/infoconn";
        String zipPath = getExternalCacheDir() + "/log" + "/zipdir";
        String zipFileName = "/infoconn_log.zip";
        LogCollector.i(false, true, "FilePath: " + filePath);
        File sourceDirectory = new File(filePath);
        File zipFile = new File(zipPath);
        if(!zipFile.exists()) {
            zipFile.mkdirs();
        }
        zipFile = new File(zipPath + zipFileName);
        try {
            zipManager.makeZipFiles(sourceDirectory, zipFile);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        new File(zipPath + zipFileName).delete();
    }

    @Override
    protected void onDestroy() {
        zipManager = null;
        super.onDestroy();
    }
}