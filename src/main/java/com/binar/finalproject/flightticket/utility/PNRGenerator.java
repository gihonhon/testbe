package com.binar.finalproject.flightticket.utility;

import java.util.Random;

public class PNRGenerator {
    public static final Random random = new Random();
    private PNRGenerator(){
    }

    public static String generatePNR()
    {
        int leftLimit = 48;
        int rightLimit = 90;

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
