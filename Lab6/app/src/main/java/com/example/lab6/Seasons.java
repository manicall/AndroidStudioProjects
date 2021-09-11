package com.example.lab6;

import java.time.Month;

public class Seasons {
    public static final int SUMMER = 0;
    public static final int AUTUMN = 1;
    public static final int WINTER = 2;
    public static final int SPRING = 3;

    public static int getSeason(int month) {
        final int OFFSET = 4;
        int myMonth = month + OFFSET;
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
