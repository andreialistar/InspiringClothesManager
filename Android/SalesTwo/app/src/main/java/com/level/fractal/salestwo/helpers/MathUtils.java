package com.level.fractal.salestwo.helpers;

import android.graphics.Color;

import java.util.Random;

public class MathUtils {

    public static int ClosestIntervalStepToPoint(int step, int n) {
        int offset = 0;
        int tolerance = Math.abs(step / 2);
        while (Math.abs(offset - n) > tolerance) {
            offset += step;
        }

        return offset;
    }

    public static int GetRandomColor(int alphaStrength, int redStrength, int greenStrength, int blueStrength) {
        Random random = new Random();
        int a = random.nextInt(alphaStrength);
        int r = random.nextInt(redStrength);
        int g = random.nextInt(greenStrength);
        int b = random.nextInt(blueStrength);

        return Color.argb(a, r, g, b);
    }
}
