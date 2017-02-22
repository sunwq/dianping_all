package com.ucdat.dp.spider.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by 飞机 on 2016/12/30.
 */
public class SplitFile {

    /**
     * @param args
     */
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int rows = 5000;
        String sourceFilePath = "D:/data/japan/meishi_success_task_ex.json";
        String targetFilePath = "D:/data/japan/split";
        splitDataToSaveFile(rows,sourceFilePath,targetFilePath);
    }

    /**
     * 按行分割文件
     * @param rows 为多少行一个文件
     * @param sourceFilePath 为源文件路径
     * @param targetDirectoryPath 文件分割后存放的目标目录
     */
    public static void splitDataToSaveFile(int rows, String sourceFilePath,
                                    String targetDirectoryPath) {
        long start1 = System.currentTimeMillis();

        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetDirectoryPath);
        if (!sourceFile.exists() || rows <= 0 || sourceFile.isDirectory()) {
            return;
        }
        if (targetFile.exists()) {
            if (!targetFile.isDirectory()) {
                return;
            }
        } else {
            targetFile.mkdirs();
        }
        try {

            InputStreamReader in = new InputStreamReader(new FileInputStream(sourceFilePath),"UTF-8");
            BufferedReader br=new BufferedReader(in);

            BufferedWriter bw = null;
            String str = "";
            String tempData = br.readLine();
            int i = 1, s = 0;
            long start2 = System.currentTimeMillis();
            while (tempData != null) {
                str += tempData + "\r\n";
                if (i % rows == 0) {
                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                            targetFile.getAbsolutePath() + "/" +  sourceFile.getName() +"_" + (s+1) +".json"), "UTF-8"),1024);

                    bw.write(str);
                    bw.close();

                    str = "";
                    start2 = System.currentTimeMillis();
                    s += 1;
                }
                i++;
                tempData = br.readLine();
            }
            if ((i - 1) % rows != 0) {

                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                        targetFile.getAbsolutePath() + "/" +  sourceFile.getName() +"_" + (s+1) +".json"), "UTF-8"),1024);
                bw.write(str);
                bw.close();
                br.close();

                s += 1;
            }
            in.close();

        } catch (Exception e) {
        }
    }
}
