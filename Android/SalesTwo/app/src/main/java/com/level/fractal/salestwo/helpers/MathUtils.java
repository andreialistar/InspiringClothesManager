package com.level.fractal.salestwo.helpers;

public class MathUtils {

    public static int ClosestIntervalStepToPoint(int step, int n) {
        int offset = 0;
        int tolerance = Math.abs(step / 2);
        while (Math.abs(offset - n) > tolerance) {
            offset += step;
        }

        return offset;
    }
}
