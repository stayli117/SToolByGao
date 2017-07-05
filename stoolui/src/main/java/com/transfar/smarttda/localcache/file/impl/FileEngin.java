package com.transfar.smarttda.localcache.file.impl;

import com.transfar.smarttda.core.LogTDA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by stayli on 2017/3/30.
 */

public class FileEngin {
    public FileEngin() {
    }

    public void printInstant(String var1, File var2) {
        if(!var2.exists()) {
            String var3 = var2.getPath().substring(0, var2.getPath().lastIndexOf("/") + 1);
            File var4 = new File(var3);
            if(!var4.exists()) {
                var4.mkdirs();
            }

            try {
                var2.createNewFile();
            } catch (IOException var7) {
                LogTDA.SDKinfo("FileEngin", "文件IO错误：" + var7);
            }
        }

        var1 = var1 + "\r\n";

        try {
            FileOutputStream var8 = new FileOutputStream(var2, true);
            var8.write(var1.getBytes());
            var8.close();
        } catch (IOException var6) {
            LogTDA.SDKinfo("FileEngin", "文件IO错误：" + var6);
        }

    }

    public String newFileName() {
        SimpleDateFormat var1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return var1.format(new Date()) + "_TDA_Log.txt";
    }
}
