package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Arrays.sort(args, 1, args.length); //сортируем
        String resultFileName = args[0];
        ArrayList<FileInputStream> fileNamePart = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            fileNamePart.add(new FileInputStream(args[i])); // т.к. аргументов может быть несколько, добавляем их в лист
        }
        ZipInputStream zis = new ZipInputStream(new SequenceInputStream(Collections.enumeration(fileNamePart)));
        FileOutputStream fos = new FileOutputStream(resultFileName);
        ZipEntry ze;
        byte[] buffer = new byte[1024 * 1024];//1MB buffer
        while ((ze = zis.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName());
            int byteBuffer;
            while ((byteBuffer = zis.read(buffer)) != -1) {
                fos.write(buffer, 0, byteBuffer);
            }
            zis.closeEntry();
        }
        fos.close();
        zis.close();
    }
}
