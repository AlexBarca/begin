package com.htp;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Main {

    public static void main(String[] args) {
        System.out.println(generateWelcomePhrase());
        System.out.println(generateInt());
        System.out.println(generateDouble());
        System.out.println(generateFloat());
        System.out.println(generateIntWithWelcomePhrase());
        System.out.println(generateLong());
        System.out.println(getPhrase());
    }

    private static float generateFloat() {
        return RandomUtils.nextFloat();
    }

    private static double generateDouble() {
        return RandomUtils.nextDouble();
    }

    private static String generateWelcomePhrase() {
        return "My main";
    }

    public static int generateInt() {
        return RandomUtils.nextInt();
    }

    public static long generateLong() {
        return RandomUtils.nextLong();
    }

    public static String generateIntWithWelcomePhrase() {
        return "My main" + RandomUtils.nextInt();
    }

    public static String getPhrase() {
        return RandomStringUtils.random(5);
    }


}
