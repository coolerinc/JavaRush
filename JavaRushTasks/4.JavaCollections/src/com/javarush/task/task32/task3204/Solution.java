package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.util.Random;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        int MAX_SIZE = 8;
        String DIGITS = "0123456789";
        String LOCASE_CHARACTERS = "abcdefghjkmnpqrstuvwxyz";
        String UPCASE_CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
        String ALL = DIGITS + LOCASE_CHARACTERS + UPCASE_CHARACTERS;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        char[] upcaseArray = UPCASE_CHARACTERS.toCharArray();
        char[] locaseArray = LOCASE_CHARACTERS.toCharArray();
        char[] digitsArray = DIGITS.toCharArray();
        char[] allArray = ALL.toCharArray();
        Random random = new Random();
        bos.write(locaseArray[random.nextInt(locaseArray.length)]);
        bos.write(upcaseArray[random.nextInt(upcaseArray.length)]);
        bos.write(digitsArray[random.nextInt(digitsArray.length)]);
        for (int i = 3; i < MAX_SIZE; i++) {
            bos.write(allArray[random.nextInt(allArray.length)]);
        }
        return bos;
    }
}