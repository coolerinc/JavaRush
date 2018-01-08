package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

/* 
Кроссворд
*/
public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                //0    1    2    3    4    5
                {'f', 'd', 'e', 'r', 'l', 'k'},//0
                {'u', 's', 'a', 'm', 'e', 'o'},//1
                {'l', 'n', 'g', 'r', 'o', 'v'},//2
                {'m', 'l', 'p', 'r', 'r', 'h'},//3
                {'p', 'o', 'e', 'e', 'j', 'j'} //4
        };
        System.out.println(detectAllWords(crossword, "ro"));
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> list = new ArrayList<>();
        int[] startPos = new int[2];
        for (String w : words) {
            char[] letters = w.toCharArray();//Заносим каждую букву из слова в массив
            int[][] allStartLettersPos = firstLettersDetect(crossword, letters);//Ищем в кроссворде первую букву
            for (int i = 0; i < allStartLettersPos.length; i++) {
                startPos[0] = allStartLettersPos[i][0];
                startPos[1] = allStartLettersPos[i][1];
                list.add(deepSearch(crossword, letters, startPos, w));//Ищем в кроссворде остальные буквы от первой
            }
        }
        return list;
    }

    public static int[][] firstLettersDetect(int[][] crossword, char[] letters) {
        int[] pos = {0, 0};
        int counter = 0;
        int counter2 = 0;
        for (int j = 0; j < crossword.length; j++) {//проверяем сколько всего есть искомых букв
            for (int k = 0; k < crossword[0].length; k++) {
                if (letters[0] == (crossword[j][k])) counter++;
            }
        }
        int[][] allStartLettersPos = new int[counter][2];
        for (int x = 0; x < crossword.length; x++) {
            for (int y = 0; y < crossword[x].length; y++) {
                if (letters[0] == (crossword[x][y])) {//если нашли букву, отмечаем ее координаты
                    System.out.println(letters[0] + " " + y + " " + x);
                    pos[0] = y;
                    pos[1] = x;
                    allStartLettersPos[counter2][0] = y;//заносим в массив
                    allStartLettersPos[counter2][1] = x;
                    counter2++;
                }
            }
        }
        return allStartLettersPos;
    }

    public static Word deepSearch(int[][] crossword, char[] letters, int[] startPos, String word) {
        Word tmp = null;
        int[] endPos = {0, 0};
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}, {-1, 1}, {-1, -1}};
        boolean isEnd = false;
        int[] vector = {1, 0};
        while (!isEnd) {
            {
                for (int i = 0; i < directions.length; i++) {
                    endPos[0] = startPos[0];
                    endPos[1] = startPos[1];
                    System.out.println(i + " цикл направления. стартовые координаты " + endPos[0] + " " + endPos[1]);
                    endPos[0] += directions[i][1];
                    endPos[1] += directions[i][0];
                    try {
                        System.out.println("ищем букву " + letters[1] + ". мы на позиции " + (char) crossword[endPos[1]][endPos[0]] + " коорд " + endPos[1] + " " + endPos[0]);
                        if (letters[1] == crossword[endPos[1]][endPos[0]]) {
                            System.out.println(letters[1] + " coord " + endPos[0] + " " + endPos[1]);
                            vector[0] = directions[i][0];
                            vector[1] = directions[i][1];
                            System.out.println("vector " + vector[1] + " " + vector[0]);
                            if (letters.length < 3) {//проверяем, если букв в слове меньше 3
                                System.out.println("все буквы найдены");
                                tmp = new Word(word);
                                tmp.setStartPoint(startPos[0], startPos[1]);
                                tmp.setEndPoint(endPos[0], endPos[1]);
                                break;
                            } else {
                                for (int k = 2; k < letters.length; k++) {
                                    endPos[0] += vector[1];
                                    endPos[1] += vector[0];
                                    if (letters[k] == crossword[endPos[1]][endPos[0]]) {
                                        System.out.println(letters[k] + " coord " + endPos[0] + " " + endPos[1]);
                                        if (k == letters.length - 1) {
                                            System.out.println("все буквы найдены");
                                            tmp = new Word(word);
                                            tmp.setStartPoint(startPos[0], startPos[1]);
                                            tmp.setEndPoint(endPos[0], endPos[1]);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        //System.out.println("искали " + letters[1] + ". вышли за пределы " + endPos[1] + " " + endPos[0]);
                    }

                    System.out.println("__________________");
                }
                isEnd = true;
            }
        }
        return tmp;
    }

    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}
