package com.example.lab6;

import java.time.Month;

public class Seasons {
    // константы определяющие времена года
    public static final int SUMMER = 0;
    public static final int AUTUMN = 1;
    public static final int WINTER = 2;
    public static final int SPRING = 3;

    // получить время года, на основе введенного месяца
    public static int getSeason(int month) {
        /*смещение полученного месяца на 4
        тк для значений 0-3 используются
        константы времен года*/
        final int OFFSET = 4;
        int myMonth = month + OFFSET;
        // константы определяющие месяц года
        final int JANUARY = 4;
        final int FEBRUARY = 5;
        final int MARCH = 6;
        final int APRIL = 7;
        final int MAY = 8;
        final int JUNE = 9;
        final int JULY = 10;
        final int AUGUST = 11;
        final int SEPTEMBER = 12;
        final int OCTOBER = 13;
        final int NOVEMBER = 14;
        final int DECEMBER = 15;
        // выбор времени года
        // на основе месяца со смещением
        switch (myMonth) {
            case JUNE:
            case JULY:
            case AUGUST:
                return SUMMER;
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                return AUTUMN;
            case DECEMBER:
            case FEBRUARY:
            case JANUARY:
                return WINTER;
            case MARCH:
            case APRIL:
            case MAY:
                return SPRING;
            default:
                return -1;
        }
    }

}
