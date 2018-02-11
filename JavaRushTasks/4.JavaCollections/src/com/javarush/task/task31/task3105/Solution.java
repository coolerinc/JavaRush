package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        String zipFileName = args[1];
        File file = new File(fileName);
        Map<String, byte[]> map = new HashMap<>();

        // Потоки будут закрыты автоматически т.к. используем try-with-resources
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {//читаем содержимое ZIP архива
                int data;
                byte[] buffer = new byte[1024 * 1024];//1 Mb buffer
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(buffer.length);
                while ((data = zis.read(buffer, 0, buffer.length)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, data);
                }
                byteArrayOutputStream.close();
                map.put(entry.getName(), byteArrayOutputStream.toByteArray());
            }
        }
        // Потоки будут закрыты автоматически т.к. используем try-with-resources
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            for (Map.Entry<String, byte[]> zipEntry : map.entrySet()) {
                if (fileName.equals(zipEntry.getKey().substring(zipEntry.getKey().lastIndexOf("/") + 1))) continue;
                zip.putNextEntry(new ZipEntry(zipEntry.getKey()));
                zip.write(zipEntry.getValue());
            }
            zip.putNextEntry(new ZipEntry("new/" + fileName));
            Files.copy(file.toPath(), zip);
        }
    }
}